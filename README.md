
#Gheorghe Marius Razvan 324CA

# GlobalWaves2 Etapa2

Am folosit scheletul oficial

In etapa a 2 a am implementat sistemul de pagini si am adaugat noi entitati fata de prima etapa.

##Implementare

  In cadrul sistemului de pagini putem vedea pagina curenta, 
unde se afla userul prima oara pe platforma in momentul adaugarii.
  Userul normal are 2 pagini --HomePage-- si --LikedPage-- dar
  poate ajunge pe pagina unui artist/host atunci cand il cauta 
  si il selecteaza.Am implementat aceasta optiune printr o 
  variabila in clasa User "pageType" care se modifica in functie 
  de cautare.Pentru tipul artist/host fac aceasta modificare in metoda "search"
  din clasa User.
  Pentru a afisa datele paginii specifice fiecarui utilizator
  am creat cate o clasa in pachetul --pages-- pentru fiecare tip de pagina
  Am folosit chatGPT pentru a ma ajuta la scrierea formatului necesar.
  
  Pentru fiecare entitate noua(artist/host) am creat cate o clasa in care
  se implementeaza pentru fiecare comenzile pe care le poate suporta.
  Aceste entitati noi create sunt adaugate prin intermediul Adminului
  Acesta poate adauga si sterge un user/artist/host prin intermediul 
  metodelor --addUser-- --deleteUser-- implementate in clasa acestuia.
  
  Artistul poate adauga albume, evenimente sau merch pe care le gestioneaza
  cu operatiile implementate in clasa Artist.Am folosit chatGPT la adaugarea 
  evenimentelor explicit la parsarea campului date(parseDate, ValidateDate)
  in caz ca artistul vrea sa adauge ceva din cele enumerate verifica 
  ca nu cumva sa mai existe in listele initiale.
  In acelasi timp se pot sterge albume,evenimente sau merch cu tot cu continutul
  lor dar cu conditia ca nu cumva sa fie ascultate de alti utilizatori.
  Sunt implementate explicit metodele necesare pentru aceste comenzi.
  
  Hostul poate adauga prin intermediul metodelor implementate in clasa "Host"
  podcasturi,anunturi si le poate si sterge doar sa nu fie ascultate de alti 
  utilizatori.
  
  Programul poate construi si colectii cum ar fi formate din 
  toti utilizatorii unde la implementare folosesc {stream().map}
  Poate crea si topuri de albume in functie de numarul de like uri.
  
  Ca design patterns am folosit Singleton implementat in LIBRARYINPUT si 
  apelat in metoda action.
  
  
  
  
  
