// Testprogram för att läsa Holdingregister över Modbus TCP

import java.io.* ;
import java.net.* ;
import java.util.*;

class ModbusTCP{ 

//Initiera modbus socket med inkommande argument(IPadress, SlavID, Startadress, Antal register)

public static void main(String argv[]) { if (argv.length < 4) {
      								System.out.println("Mata in: java ModbusTCPMaster IPadress SlavID Startadress Antalregister");
      		       						System.out.println("T.ex java ModbusTCPMaster 192.168.20.23 1 0 10");
      		       						return;
    		     			    		      }
    		     			try {
      						String ip_adrs = argv[0];
      						int unit = Integer.parseInt(argv[1]);
      						int reg_no = Integer.parseInt(argv[2]);
      						int num_regs = Integer.parseInt(argv[3]);
      						System.out.println("IPadress = "+ip_adrs+" SlavID = "+unit+" Startadress = "+
      						reg_no+" Antal register = "+num_regs);

      						// Uprätta en socket över port 502

      						Socket es = new Socket(ip_adrs,502);
      						OutputStream os= es.getOutputStream();
      						FilterInputStream is = new BufferedInputStream(es.getInputStream());
      						byte obuf[] = new byte[261];
      						byte ibuf[] = new byte[261];
      						int c = 0;
      						int i;

      						// Bygg telegram för förfrågning
      						// Byte 0-1 Transaction ID skall indexeras vid flera förfrågningar, 
						// Byte 2-3 Protocol ID är alltid 0, Byte 5 Längd på resten av telegrammet(MSB)      						

						for (i=0;i<5;i++) obuf[i] = 0; 
      						obuf[5] = 6; // Byte 6 Längd på resten av telegrammet(LSB) 
      						obuf[6] = (byte)unit; // Byte 7 SlavID
      						obuf[7] = 3; // Byte 8 telegramtyp (Läs holdingregisters)
      						obuf[8] = (byte)(reg_no >> 8); // Byte 9 Startadress(MSB)
      						obuf[9] = (byte)(reg_no & 0xff); // Byte 10 Startadress(LSB)
      						obuf[10] = (byte)(num_regs >> 8); // Byte 11 Antal register(MSB)
      						obuf[11] = (byte)(num_regs & 0xff); // Byte 12 Antal register(LSB)

      						// Skicka telegram
      						os.write(obuf, 0, 12);

     						 // Läs svarstelegram
      						i = is.read(ibuf, 0, 261);
      						if (i<9) { if (i==0) { System.out.println("Oväntad stängning från server");
        							     } else { System.out.println("Telegrammet är för kort - "+i+" tecken");
						}
      					} else if (0 != (ibuf[7] & 0x80)) { System.out.println("MODBUS felmeddelande - typ "+ibuf[8]);
      									  } else if (i != (9+2*num_regs)) { System.out.println("fel antal register tillbaka "+i+" förväntat antal"+(9+2*num_regs));
     		 											  } else { for (i=0;i<num_regs;i++) { int w = (ibuf[9+i+i]<<8) + ibuf[10+i+i];
																	      System.out.println("Holdingregister "+i+" = "+w);
																	    }
													  	 }

      														 // Stäng ned anslutning
      														 es.close(); 
													  } catch (Exception e) { System.out.println("exception :"+e);
    								     }
  							}
					}