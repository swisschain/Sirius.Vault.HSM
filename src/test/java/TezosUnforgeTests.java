import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.utils.tezos.Base58Helper;
import io.swisschain.crypto.utils.tezos.forging.LocalForge;
import io.swisschain.crypto.utils.tezos.forging.operations.RevealContent;
import io.swisschain.crypto.utils.tezos.forging.operations.TransactionContent;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TezosUnforgeTests {
    @Test
    public void  should_unforge_transfer() throws IOException, NoSuchAlgorithmException, InvalidInputsException {
        var source = "5m82GxuCxgGzzgQDBE5NLfvGhZ5w2WHJkASqJoPp21yw797a4qBUkjL7HepNcZ5ARrkzweAvsEmS92SC93BqVzQSTRGNTXe3dy55XCA13UbntiyFDfejppX";
        var bytes = Base58Helper.decode(source);

        var subject = new LocalForge();
        var result = subject.UnforgeOperations(bytes);

        Assert.assertEquals(1, result.size());

        var transactionContent = (TransactionContent) result.toArray()[0];

        Assert.assertEquals(1000, transactionContent.Amount);
        Assert.assertEquals("tz1W89m1sj5iyN6FtDkRrgi16cYJtzJD2P7Y", transactionContent.Destination);
        Assert.assertEquals("tz1VygLchG6F3xCG6p6LViiGRfdUypXCWbJV", transactionContent.Source);
        Assert.assertEquals(5000, transactionContent.Fee);
        Assert.assertEquals(434082, transactionContent.Counter);
        Assert.assertEquals(15000, transactionContent.GasLimit);
        Assert.assertEquals(0, transactionContent.StorageLimit);
        Assert.assertNull(transactionContent.Parameters);
    }

    @Test
    public void  should_unforge_reveal() throws IOException, NoSuchAlgorithmException, InvalidInputsException {
        var source = "MkKmShezRW2dkNu8z1U6nS2oya3HiFs5Sm9YRFsr8LMqSYWAdg55VD1XbnE7JYg2YJnjWqi1dphj82zt5LUfzZ4V1LkVcUjz3h9LGoJdt1qbXE1z8mYC9AaXm9VVnAuevbKeGkpraL7oBKFAJFAR4y1F1XAwqRT1pmGFg4saqYAuinESy9nmKr99WpM444e1i1r9KmoFkUiqu";
        var bytes = Base58Helper.decode(source);

        var subject = new LocalForge();
        var result = subject.UnforgeOperations(bytes);

        Assert.assertEquals(2, result.size()); //reveal + transfer

        var revealContent = (RevealContent) result.toArray()[0];

        Assert.assertEquals("edpkvWbPgoa5yGDVDC4Hpy7sde5WpjUpMULpsUq6MKRaCSB9fJzZT8", revealContent.PublicKey);
        Assert.assertEquals("tz1QXEPvXbEx7DdsyCxBvaWvUDWmYcByDHL7", revealContent.Source);
        Assert.assertEquals(0, revealContent.Fee);
        Assert.assertEquals(441671, revealContent.Counter);
        Assert.assertEquals(1500, revealContent.GasLimit);
        Assert.assertEquals(0, revealContent.StorageLimit);
    }

    @Test
    public void  should_unforge_smart_contract() throws IOException, NoSuchAlgorithmException, InvalidInputsException {
        var source = "6oMxdi8zPsiy4fmurVGpWvcr59C5aLkN8ppJNY82cF2hPrP9LC82AjMrKqqwBpS4YW8ghh5QKPeHxpsrosBmwk31WQXm12ZvzZ98W2XrYoSw6fMveevXBgeXh7kfEiekzdx62YjHrzZTmfscWQgx6cqJAm9JoZXUELfL9thrg13QBFoCW4z9nXQdr2N3aFbcgV2piEAEu95HBhCfsuwdqbzHd1EhNRBhVjMdAMrAmPHC9KCVDZ6DFscUmQDCiAnRiSpH";
        var bytes = Base58Helper.decode(source);

        var subject = new LocalForge();
        var result = subject.UnforgeOperations(bytes);
        Assert.assertEquals(1, result.size());

        var transactionContent = (TransactionContent) result.toArray()[0];

        Assert.assertEquals(0, transactionContent.Amount);
        Assert.assertEquals("KT1JiQhr9EXHL88U3hjJH6FkPv8wWdVYvwtg", transactionContent.Destination);
        Assert.assertEquals("tz1fkoo5CyFUJHfbeCtwpDwQ7YTmHBDSqfYY", transactionContent.Source);
        Assert.assertEquals(15000, transactionContent.Fee);
        Assert.assertEquals(293743, transactionContent.Counter);
        Assert.assertEquals(100000, transactionContent.GasLimit);
        Assert.assertEquals(257, transactionContent.StorageLimit);
        Assert.assertNotNull(transactionContent.Parameters);
        Assert.assertEquals("transfer", transactionContent.Parameters.Entrypoint);
        //TODO Read parameters
    }
}
