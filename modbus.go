package main

import (
	"fmt"
	"os"
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
	ipadress := os.Args[1]
	slaveid := os.Args[2]
	startadress := os.Args[3]
	num_of_registers := os.Args[4]
	fmt.Printf("Ipadress: %v \nSlaveid; %v \nStartadress: %v\nNumber of registers: %v\n", ipadress, slaveid, startadress, num_of_registers)
}
