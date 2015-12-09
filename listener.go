package main

import (
	"fmt"
	"net"
)

func main() {
	ln, err := net.Listen("tcp", ":8080")
	if err != nil {
		fmt.Println(err)
	}
	for {
		conn, err := ln.Accept()
		if err != nil {
			fmt.Println(err)
		}
		go handleConnection(conn)
	}

}

func handleConnection(conn net.Conn) {
	var ibuf, obuf []byte
	_, err := conn.Read(ibuf)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println(ibuf)
	obuf[0] = byte(1)
	_, err = conn.Write(obuf)
	if err != nil {
		fmt.Println(err)
	}

}
