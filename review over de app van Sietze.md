[sietze](https://github.com/sietzeberends/sietzeberendspset6)
Weet niet of dat een verschil in versies zijn maar.
EditText getEmail;
en getEmail = (EditText) findViewbyid(r.id.getEmail)
de (EditText) hoef je hier niet meer te gebruiken want je heb al eerder  boven al aan gegeven dat het een EDITText moet zijn.

voor de rest heel mooi en netjes

Readme
plaatjes iets te groot
en je bent je Bettercodehub button vergeten

String email;
String password;
Button login;
Button register;
EditText getEmail;
EditText getPassword;
Integer state;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_register_layout);

    mAuth = FirebaseAuth.getInstance();
    mDatabase = FirebaseDatabase.getInstance().getReference();

    getEmail = (EditText) findViewById(R.id.getEmail);
    getPassword = (EditText) findViewById(R.id.getPassword);
    login = (Button) findViewById(R.id.login);
    register = (Button) findViewById(R.id.register);
