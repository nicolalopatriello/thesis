# Spring Example Project

Per visionare correttamente il progetto bisogna scaricare il plugin "lombok" per il proprio IDE.
Per avviare, eseguire Application da IDE. Verrà eseguito Spring avente DB in memory (H2).
Per provare le API REST si può caricare il file `postman_collection.json` in postman e provare:

- GET `/users/` restituisce la lista di user paginata
- POST `/users/` fa l'signup (insert) di un user
- GET `/users/{ID}` recupera un user (ritorna 404 se non esiste)
- DELETE `/users/{ID}` cancella il user con id `ID`




