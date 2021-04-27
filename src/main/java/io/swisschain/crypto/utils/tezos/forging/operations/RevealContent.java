package io.swisschain.crypto.utils.tezos.forging.operations;

public class RevealContent extends ManagerOperationContent {
    public RevealContent(){
        Kind = "reveal";
    }
    public String PublicKey;
}
