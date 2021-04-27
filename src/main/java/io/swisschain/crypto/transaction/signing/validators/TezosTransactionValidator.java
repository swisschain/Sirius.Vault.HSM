package io.swisschain.crypto.transaction.signing.validators;
import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.utils.tezos.forging.LocalForge;
import io.swisschain.crypto.utils.tezos.forging.operations.RevealContent;
import io.swisschain.crypto.utils.tezos.forging.operations.TransactionContent;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

public class TezosTransactionValidator {

    public static void  validate(TransferDetails expected, byte[] unsignedTransaction, String pubkey)
            throws InvalidInputsException, NoSuchAlgorithmException, IOException, TransferDetailsValidationException {

        var unforged = new LocalForge().UnforgeOperations(unsignedTransaction);

        if(unforged.size() > 2){
            throw new TransferDetailsValidationException("Unexpected operation count. " +
                    "Expected 2 (reveal + transaction) at max, " +
                    "but got " + unforged.size());
        }

        var totalFee = 0;

        if(unforged.size() == 2){
            var reveal = (RevealContent) unforged.stream()
                    .filter((op) -> op instanceof RevealContent)
                    .findFirst()
                    .orElseThrow();
            totalFee += reveal.Fee;

            if(!reveal.PublicKey.equals(pubkey)){
                throw new TransferDetailsValidationException("Unexpected public key revelation." +
                        " Expected "+ pubkey + ", " +
                        "but got " + reveal.PublicKey);
            }
        }

        var transaction = (TransactionContent) unforged.stream()
                .filter((op) -> op instanceof TransactionContent)
                .findFirst()
                .orElseThrow();
        totalFee += transaction.Fee;

        if(totalFee > ToMicrotez(expected.getFeeLimit())){
            throw new TransferDetailsValidationException("Unexpected fee value." +
                    " Expected "+ ToMicrotez(expected.getFeeLimit()) + " microtez, " +
                    "but got " + totalFee + " microtez");
        }

        var isNativeTransfer = expected.getAsset().getAddress() == null;
        if(isNativeTransfer){
            validateNativeTransfer(expected, transaction);
        }else{
            validateSmartContractTransfer(expected, transaction);
        }
    }

    private static void validateNativeTransfer(TransferDetails expected, TransactionContent actual)
            throws TransferDetailsValidationException {
        if(!actual.Source.equals(expected.getSourceAddress().getAddress())) {
            throw new TransferDetailsValidationException("Unexpected source address. " +
                    "Expected " + expected.getSourceAddress().getAddress() + ", " +
                    "but got " + actual.Source);
        }

        if(!actual.Destination.equals(expected.getDestinationAddress().getAddress())){
            throw new TransferDetailsValidationException("Unexpected destination address. " +
                    "Expected " + expected.getDestinationAddress().getAddress() + ", " +
                    "but got " + actual.Destination);
        }

        if(actual.Amount != ToMicrotez(expected.getAmount())){
            throw new TransferDetailsValidationException("Unexpected amount. " +
                    "Expected " + ToMicrotez(expected.getAmount()) + " microtez, " +
                    "but got " + actual.Amount + " microtez");
        }
    }

    private static void validateSmartContractTransfer(TransferDetails expected, TransactionContent actual)
            throws TransferDetailsValidationException {
        if(!actual.Destination.equals(expected.getAsset().getAddress())){
            throw new TransferDetailsValidationException("Unexpected smart contract address. " +
                    "Expected " + expected.getDestinationAddress().getAddress() + ", " +
                    "but got " + actual.Destination);
        }

        //TODO unforge source/destination/amount parameters from FA12/FA2 transfers and validate them
    }

    private static long ToMicrotez(BigDecimal source){
        return source.multiply(BigDecimal.valueOf(Math.pow(10, 6))).longValue();
    }
}
