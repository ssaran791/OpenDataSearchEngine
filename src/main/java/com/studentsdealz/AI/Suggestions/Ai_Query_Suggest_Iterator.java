package com.studentsdealz.AI.Suggestions;

/**
 * Created by saran on 8/12/17.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;


class Ai_Query_Suggest_Iterator implements InputIterator
{
    private Iterator<AI_Query_Suggest_Item> productIterator;
    private AI_Query_Suggest_Item currentAIQuerySuggestItem;

    Ai_Query_Suggest_Iterator(Iterator<AI_Query_Suggest_Item> productIterator) {
        this.productIterator = productIterator;
    }

    public boolean hasContexts() {
        return true;
    }

    public boolean hasPayloads() {
        return true;
    }

    public Comparator<BytesRef> getComparator() {
        return null;
    }

    // This method needs to return the key for the record; this is the
    // text we'll be autocompleting against.
    public BytesRef next() {
        if (productIterator.hasNext()) {
            currentAIQuerySuggestItem = productIterator.next();
            try {
                return new BytesRef(currentAIQuerySuggestItem.name.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new Error("Couldn't convert to UTF-8");
            }
        } else {
            return null;
        }
    }

    // This method returns the payload for the record, which is
    // additional data that can be associated with a record and
    // returned when we do suggestion lookups.  In this example the
    // payload is a serialized Java object representing our product.
    public BytesRef payload() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(currentAIQuerySuggestItem);
            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new Error("Well that's unfortunate.");
        }
    }

    // This method returns the contexts for the record, which we can
    // use to restrict suggestions.  In this example we use the
    // regions in which a product is sold.
    public Set<BytesRef> contexts() {

            return null;

    }

    // This method helps us order our suggestions.  In this example we
    // use the number of products of this type that we've sold.
    public long weight() {
        return currentAIQuerySuggestItem.num_of_hits;
    }
}
