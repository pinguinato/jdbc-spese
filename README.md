# Jdbc Spese

## Esempio facile Java con Jdbc

**Database usato:** MySql

**resources/db.properties** file per le properties di connessione al db

    # mySql DB Properties
    MYSQL_DB_DRIVER_CLASS=com.mysql.jdbc.Driver
    MYSQL_DB_URL=jdbc:mysql://localhost:3306/spese
    MYSQL_DB_USERNAME=root
    MYSQL_DB_PASSWORD=


**model/Spesa.java** è il modello Java che identifica una spesa, come ad esempio:

- Acquisto scheda video
- Ricarica cellulare
- ecc..ecc...

La classe Spesa, al momento, ha i seguenti campi:

    private Integer idSpesa;
    private String titoloSpesa;
    private String descrizioneSpesa;
    private String autoreSpesa;
    private Double totaleSpesa;

che sono sufficienti per descrivere quale spesa è stata fatta. Al momento,
 non interessa tener traccia della data di una spesa.

**dataIntegration/MySqlConnection.java** classe Java che fornisce i metodi 
essenziali per gestire l'oggetto Connection di Java che ci serve per effettuare 
la connessione al database.

**test** Esempi di Junit 5 test sul progetto, i test sulla connessione, non 
funzionano se il database non è raggiungibile e/o le properties di connessione non sono 
definite.
