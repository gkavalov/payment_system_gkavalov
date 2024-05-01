package gkavalov.emerchantpay.payment.system.model.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TransactionDtoDeserialiser extends StdDeserializer<TransactionDto> {

    public TransactionDtoDeserialiser() {
        super(TransactionDto.class);
    }

    @Override
    public TransactionDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final JsonNode node = p.getCodec().readTree(p);
        return TransactionDtoFactory.makeTransaction(node);
    }
}
