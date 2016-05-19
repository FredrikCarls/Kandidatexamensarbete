# Kandidatexamensarbete
Koden till implementationsdelen av ett kandidatexamensarbete inom Information Extraction

## Metod
Programmet består av två delar, basfallet och en mer avancerad del som kommer implementera diverse metoder inom ämnet språkteknologi för att förbättra resultatet. Den andra delen kallar vi för Named Entity Recognition (NER) implementationen.
Gemensamt för programmet är att det kommer läsa in artiklar från ett sju olika svenska nyhetssidor; Aftonbladet, Expressen, Dagens Nyheter (DN), Dagens Industri (DI), Göteborgs-Posten (GP), Metro samt Svenska Dagbladet (SvD). Dessa artiklar hämtas från retriever som agerar oberoende databas för samtliga svenska nyhetstidningar. Vi har valt att begränsa oss till att hämta ut 500 artiklar från intervall av 7 dagar. Den ena testmängden hämtades 13/4 och den andra 20/4. Ur datamängden extraherar programmet själva artikeltexterna som sedan bearbetas av programmet.

###### Basfallet
Basfallet kommer vara en enkel implementation vars enda uppgift är att läsa in artikeltexten och sedan ord för ord kontrollera samt beräkna förekomsten av könsspecifika pronomen.

###### NER implementationen
NER implementationen kommer förutom att utnyttja basfallets funktion även identifiera personnamn i texten. Eftersom personnamn alltid skriv med versal, d.v.s. stor bokstav så kommer versalord i texten vara till intresse för programmet. Programmet tolkar alltså versalord i texten och plockar ut de som uppfyller satta krav för personnamn. Kraven för att ett versalord ska utgöra ett personnamn består av kontroller mot namnlistor för de olika könen och diverse metoder för att tolka om det faktiskt är ett namn. En viktig metod är att särskilt undersöka de versalord som kommer efter en punkt, så kallade punktord.  Detta eftersom dessa lätt kan misstolkas som namn på grund av att de har en stor bokstav. Vanliga ord som “Han”, “Har”, “Hon” är namn som förekommer i Sverige men som i en text i början av en mening förmodligen inte ska tolkas som ett namn. Sådana ord placeras i separata listor för att vidare undersöka deras rimlighet. Om de sedan visas vara korrekta och faktiskt syftar till en person återplaceras de i listan över potentiella personnamn. En annan viktig metod är tolkningen av efternamn. Ofta presenteras personer i nyhetsartiklar med för och efternamn för att sedan omnämnas i form av endast den ena. Programmet löser detta på så vis att det sammankopplar efternamnsbenämningar med första förekomsten av personen i fråga. Man kan se detta som att programmet “lär” sig vilka personer som omnämns i texten för att sedan kontrollera om personerna omnämns på annat vis än på det de redan finns lagrade.

###### Metodval
Det finns många olika sätt att gå tillväg på för att analysera texter. Vi testade en del olika med beslutade oss tillslut för att läsa texten och lagra samtliga förekomster av versalord. Programmet fungerar sedan som en tratt då vi till en början har en stor datamängd för att sedan låta den gå igenom diverse tester och därigenom tunnas ut.

## Programmet
Programmet är skrivet i java och består av 5 klasser. Huvudklassen “Genustaggaren.java” initierar de andra klasserna, läser in texterna och skapar versalOrdListan som innehåller alla versalord samt punktOrdListan som innehåller de versalorden som förekommer i början av en mening. Därefter kör huvudprogrammet de övriga klasserna, kontrollerar kontexten för hen-benämningar, räknar förekomsterna och presenterar resultatet.

###### Basfallet
Basfallet utgör den simpla implementationen som lagrar tre olika listor med könsspecifika pronomen, en för varje kategori; kvinnor, män och könsneutralt. Därefter går programmet igenom texten ord för ord och om något av de könsspecifika pronomenen identifieras så ökar den förekomsten för denna könskategori i enlighet.

###### Ordlistan
Ordlistan tar emot versalOrdListan och låter dess versalords kombinationer genomgå diverse tester mot namnlistorna för att sedan placera de förmodliga personerna som omnämns i personListan, vilket är en Hashtable som innehåller textens personer samt antalet förekomster av dessa i texten.  Därefter skapar vi MatchListorna, en för varje könskategori som innehåller förnamnen i personListan.

###### NER
NER klassen tar även den emot versalOrdListan. Första delen av programmet behandlar punktOrdListan. Vi jämför punktOrdListan mot MatchListorna som vi skapade i “Ordlista.java” och skapar osäkerhetsListor med de ord som matchas. De osäkra orden tas bort från Matchlistorna för att sedan efter strikt kontroll antingen läggas tillbaka eller förbli borttagna. Med antagandet att personer oftast nämns en gång i texten med för och efternamn ger vi osäkerhetslistorna kriteriet att orden minst en gång skall ha förekommit med efternamn. Om detta är fallet så tar programmet tillbaka dem till MatchListorna. Slutligen tar programmet bort alla personer ur personListan som inte förekommer i MatchListorna. När personListorna sen är färdigställda så går programmet än en gång igenom versalOrdListan för att identifiera benämningar av enbart efternamn och öka förekomsterna av de påverkade personerna.
