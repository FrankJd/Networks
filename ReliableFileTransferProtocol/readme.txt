The implementation splits into two parts running on different end systems: Sender and Receiver.
The job of this distributed application is to reliably deliver a file from Sender to Receiver.
Sender program is a command line application that has 5 arguments:
 <host address of the receiver>
 <UDP port number used by the receiver to receive data from the sender>
 <UDP port number used by the sender to receive ACKs from the receiver>
 <name of the file to be transferred>
 <timeout> integer number for timeout (in microseconds)
in the given order.
Receiver program is an application with the GUI. Minimal GUI requirements are:
 A text-field to input <host address of the sender>
 A text-filed to input <UDP port number used by the sender to receive ACKs from the
receiver>
 A text-filed to input <UDP port number used by the receiver to receive data from the
sender>
 A text-filed to input <name of the file to write received data>
 “TRANSFER” button
 A filed that shows the “current number of received in-order packets”
