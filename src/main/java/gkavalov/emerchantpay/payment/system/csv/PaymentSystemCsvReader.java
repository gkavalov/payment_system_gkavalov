package gkavalov.emerchantpay.payment.system.csv;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


// TODO This is not thread safe
public class PaymentSystemCsvReader<T> implements AutoCloseable {

    private final Reader reader;
    private final Iterator<T> csvReader;
    private Set<T> readRowObjects = new HashSet<>();
    private int readValidRowsCount = 0;

    public PaymentSystemCsvReader(final InputStream inputStream, final Class<? extends T> rowType) {
        reader = new InputStreamReader(inputStream);
        csvReader = new CsvToBeanBuilder<T>(reader)
                .withType(rowType)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                //.withVerifier() TODO add a custom verifier
                //.withMappingStrategy() TODO add a mapping strategy to solve any erroneous input, e.g. trailing white spaces
                .build()
                .iterator();
    }

    public boolean readNextNLines(final int linesToRead) {
        for (int i = 0; i < linesToRead && csvReader.hasNext(); i++) {
            readRowObjects.add(csvReader.next());
            readValidRowsCount++;
        }
        return !readRowObjects.isEmpty() || csvReader.hasNext();
    }

    public Set<T> getReadRowObjects() {
        final Set<T> readRowObjectsCopy = new HashSet<>(readRowObjects);
        readRowObjects = new HashSet<>();
        return readRowObjectsCopy;
    }

    public long getReadValidRowsCount() {
        return readValidRowsCount;
    }

    @Override
    public void close() throws IOException {
        reader.close();
        readRowObjects = new HashSet<>();
        readValidRowsCount = 0;
    }
}
