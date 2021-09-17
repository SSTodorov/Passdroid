"""This is going to be the walking skeleton for my project!"""
from smartcard.System import readers
from smartcard.util import toHexString
from smartcard.ATR import ATR
from pynput.keyboard import Key, Controller
from smartcard.Exceptions import NoCardException
import time
import sys

acs = readers()
connection = acs[0].createConnection()
logger = open("logfile.txt", "w")

while True:

    def ready():
        try:
            connection.connect()
            return True
        except NoCardException:
            return False

    if ready():
        logger.write("Starting...")
        DIRECT = [0xFF, 0x00, 0x00, 0x00, 0x16, 0xD4, 0x40, 0x01, 0x00, 0xA4, 0x04, 0x00, 0x07, 0xF0, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06]
        data, sw1, sw2 = connection.transmit(DIRECT)
        status = "%x %x" % (sw1, sw2)
        del data[0:3]
        raw_message = []
        message = ""
        keyboard = Controller()

        if status == 900 or 9000:
            print("All good")
            logger.write("All good...")
        else:
            logger.write(status)
            print(status)


        def datatomsg(raw):
            for numb in range(len(raw)):
                conv = chr(data[numb])
                raw_message.append(conv)
            clear_message = "".join(raw_message)
            return clear_message


        message = datatomsg(data)
        logger.write(message)


        def proccessscreds(msg):
            if "," in msg:
                credentials_list = msg.split(",")
                credentials_name = credentials_list[0]
                credentials_pass = credentials_list[1]
                username = credentials_name[6:len(credentials_name)]
                password = credentials_pass[6:len(credentials_pass)]
                credentials = [credentials_name, credentials_pass]
                return credentials
            else:
                credentials = msg[6:len(msg)]
                return credentials


        credentials_final = proccessscreds(message)
        logger.write("Writing...")
        if type(credentials_final) == list:
            for i in range(len(credentials_final)):
                keyboard.type(credentials_final[i])
                keyboard.press(Key.tab)
                keyboard.release(Key.tab)
                logger.write("Done!")

            keyboard.press(Key.enter)
            keyboard.release(Key.enter)
        else:
            keyboard.type(credentials_final)
            keyboard.press(Key.enter)
            keyboard.release(Key.enter)
            logger.write("Done!")

        time.sleep(5)
