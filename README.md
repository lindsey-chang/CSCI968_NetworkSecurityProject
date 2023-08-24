# Advanced Network Security

## Overview
A UDP programs to implement a remote login protocol.

Alice and Bob share a common password PW, which contains 8 alphanumeric characters. And the password in this project is `t1234567`. You can set it You can set it to the value you want in the `Main.java` file.

Alice stores the password in a hashed form in a password file. Alice also has a public and privacy key pair (pk, sk) for the plain RSA encryption and Bob stores the public key digest H(pk) where H is the SHA-1 hash function.
Alice wants to authenticate Bob for every remote connection request from Bob. This is done through the following identification protocol:

```
1: B → A: Username
2: A → B:pk,NA
3: B → A: RSA(pk, OTP)
4: A → B: Success/Fail
```
In the above protocol, NA is a 128-bit random string, OTP is a one-time password which is computed as 
OTP = H(H(pw), NA)
where H is the SHA-1 hash function.

The entire project will use 5 steps to achieve the above requirements.

- Step 1: Place Host and Client in two separate directories: Alice and Bob. Create a CSV password file in Alice’s directory. For simplicity, the password file contains only 1 record (Bob, H(PW)).
- Step 2: Generate a public and private key pair for the Alice, and store the generated public and private key pair (pk, sk) in a key file under Alice’s directory. Also, store H(pk) in a key file under Bob’s directory.
- Step 3: Alice executes Host. Then, Host is running and waiting for connection.
- Step 4: Bob executes Client. Client asks for input for username and password from user. Bob inputs the username and password via keyboard.
- Step 5: Then the identification protocol starts.
  - Protocol step 1: Client sends the first message containing Bob’s identity to Host.
  - Protocol step 2: Host generates NA and sends the (pk,NA) message to Client.
  - Protocol step 3: Client checks whether H(pk) matches the stored value in Bob’s key file; if not, terminate the communication; otherwise, compute OTP and send the message RSA(pk, OTP).
  - Protocol step 4: Host decrypts OTP, verifies its value and sends the notification to Client.

## Environment

Java 20.0.1

IntelliJ IDEA 2023.1.4

## Project Structure
```
 src
    ├── Alice
    │   ├── Host.java
    │   ├── RSApksk.csv
    │   └── password.csv
    ├── Bob
    │   ├── Client.java
    │   └── pkSHA_1.csv
    ├── Main.java
    ├── netsecurity
    │   ├── HashSHA_1.java
    │   ├── RSAKeyPairGenerator.java
    │   ├── RSAUtil.java
    │   └── SecRandom.java
    └── utility
        ├── ReadFile.java
        ├── SaveFile.java
        ├── SearchFile.java
        ├── StrSocketAddressPair.java
        └── UDPUtil.java
```

- Main.java contains the step 1 and 2. 
- Alice package:
  - Host.java realizes the function of Host. It runs the step 3, step5-protocol step 2, step5-protocol step 4.
  - password.csv in Alice file saves the record (Bob, H(PW)). 
  - RSApksk.csv in Alice file saves the record key pair (pk, sk). 
- Bob package:
  - Client.java realizes the function of Client. It runs the step 4, step5-protocol step 1, step5-protocol step 3.
  - pkSHA_1.csv in Bob file saves the pk SHA-1 value.
- netsecurity package contains the cryptographic methods needed to complete this project.
- utility package contains the other methods needed to complete this project.

## Deployment
### Compile and run the code in the terminal
Download the project files locally on your computer and then go to the NetworkSecurityA1 folder in your terminal.
For example, you save the NetworkSecurityA1 file in the address, which is `/User/Download/NetworkSecurityA1`. 

```bash
cd /User/Download/NetworkSecurityA1 # change the address depends on where is this file on your computer
cd ./src
javac Alice/*.java Bob/*.java netsecurity/*.java utility/*.java Main.java # complie all the code under src
java Main # run Main
```

Open a terminal window as a Host. (Still under the file `src` to run this command)
```bash
cd /User/Download/NetworkSecurityA1 # change the address depends on where is this file on your computer
cd ./src
java Alice.Host # run Host
```

Re-open a **new** terminal window as Client.  (Still under the file `src` to run this command)
```bash
cd /User/Download/NetworkSecurityA1 # change the address depends on where is this file on your computer
cd ./src
java Bob.Client # run Client
```


### Run the code in the IDEA
Open the whole Project File `NetworkSecurityA1` in your computer's IntelliJ IDEA. My Java JDK version used in my project is 20.0.1. If your version is different from mine, you need to choose to use your JDK version in IDEA.

Firstly, run Main.java.

Then, run Host.java in package Alice.

Finally, run Client.java in package Bob. Input the username and password. 
### Sample Run

Alice.Host
```
Step 3: Host is running and waiting for connection.
There is a remote connection request from Bob

Protocol Step 2: A sends B:pk,NA

Protocol Step 4: A sends B:Success/Fail
==================================
There is a remote connection request from Bob

Protocol Step 2: A sends B:pk,NA

Protocol Step 4: A sends B:Success/Fail
==================================
There is a remote connection request from test

Protocol Step 2: A sends B:pk,NA

Protocol Step 4: A sends B:Success/Fail
==================================
```
Bob.Client
```
Step 4: Client asks for input for username and password from user.
input username:
Bob
input password:
123
Step 5: Client and Host perform the identification protocol

Protocol Step 1: B sends A:Username

Protocol Step 3: B sends A:RSA(pk, OTP)
Fail
==================================

Step 4: Client asks for input for username and password from user.
input username:
Bob 
input password:
z7734116
Step 5: Client and Host perform the identification protocol

Protocol Step 1: B sends A:Username

Protocol Step 3: B sends A:RSA(pk, OTP)
Success
==================================

Step 4: Client asks for input for username and password from user.
input username:
test
input password:
z7734116
Step 5: Client and Host perform the identification protocol

Protocol Step 1: B sends A:Username

Protocol Step 3: B sends A:RSA(pk, OTP)
Fail
==================================
```

## Reference
The SHA-1 hash function algorithm: 
https://www.geeksforgeeks.org/sha-1-hash-in-java/

The RSA key pair generate algorithm and encrypt and decrypt: https://www.devglan.com/java8/rsa-encryption-decryption-java

