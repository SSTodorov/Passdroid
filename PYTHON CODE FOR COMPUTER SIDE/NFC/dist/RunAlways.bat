@echo off
:Start
NFC_test.exe
:: Wait 30 seconds before restarting.
TIMEOUT /T 30
GOTO:Start