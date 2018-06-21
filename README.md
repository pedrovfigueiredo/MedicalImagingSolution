# MedicalImagingSolution

First, you need to create a project in Eclipse and add the files on the main repository.
Remember to link the library GSON included in the "libraries" folder.

## Server
Execute with no arguments.
It has a thread to receive new clients and another one to check the validity of the blockchain.

## Client
- Add -l [optional filter] to the arguments in order to LIST the diagnoses. The filter parameter can be either a Patient or Physician's name or ID.

- Add -a [file.json]  to the arguments in order to add the diagnoses included in the JSON file into the blockchain (will be sent to the server).
