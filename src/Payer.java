interface Payer {

    float charge(float price);

    boolean isImmediate();
}
