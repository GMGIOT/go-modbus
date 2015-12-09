package main

import (
	"fmt"
	"net"
	"os"
	"strconv"
	//"strings"
)

func main() {
	//Run program with following flags:
	//IPadress
	//SlaveID
	//Startadress
	//Number of registers

	if len(os.Args) < 4 {
		panic("not enough parameters!")
	}
	var ipadress4 net.IP = net.ParseIP(os.Args[1])
	if ipadress4 == nil {
		panic("wrong ipadress format")
	}
	slaveid, err := strconv.Atoi(os.Args[2])
	if err != nil {
		fmt.Println(err)
	}
	startadress, err := strconv.Atoi(os.Args[3])
	if err != nil {
		fmt.Println(err)
	}
	num_of_registers, err := strconv.Atoi(os.Args[4])
	if err != nil {
		fmt.Println(err)
	}
	fmt.Printf("Ipadress: %v \nSlaveid; %v \nStartadress: %v\nNumber of registers: %v\n", ipadress4, slaveid, startadress, num_of_registers)

	var TCPAdress net.TCPAddr
	TCPAdress.IP = ipadress4
	TCPAdress.Port = 502
	conn, err := net.DialTCP("tcp", &TCPAdress, &TCPAdress)
	if err != nil {
		fmt.Println(err)
	}
	var obuf []byte
	obuf[0] = 0
	obuf[1] = 0
	obuf[2] = 0
	obuf[3] = 0
	obuf[4] = 0
	obuf[5] = 6 // Length of telegram
	obuf[6] = byte(slaveid)
	obuf[7] = 3
	obuf[8] = byte(startadress >> 8)
	obuf[9] = byte(startadress & 0xff)
	obuf[10] = byte(num_of_registers >> 8)
	obuf[11] = byte(num_of_registers & 0xff)
	conn.Write(obuf)
}
