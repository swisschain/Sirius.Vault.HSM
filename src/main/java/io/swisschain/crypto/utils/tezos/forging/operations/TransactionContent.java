package io.swisschain.crypto.utils.tezos.forging.operations;

public class TransactionContent extends ManagerOperationContent {

    public TransactionContent(){
        Kind = "transaction";
    }

    public long Amount;

    public String Destination;

    public Parameters Parameters;

    public class Parameters {
        public String Entrypoint;
        // TODO Read value
    }
}
