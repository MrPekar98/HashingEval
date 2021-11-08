package com.aau.evaluation;

import com.aau.evaluation.evaluators.*;
import org.apache.jena.graph.Triple;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;

public class Evaluation
{
    // Program argument is absolute path to dataset (.nt)
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Missing path to dataset (.nt)");
            System.exit(1);
        }

        else if (!new File(args[0]).exists())
        {
            System.err.println("File does not exist");
            System.exit(1);
        }

        URI datasetURI = URI.create(args[0]);
        Collection<Triple> triples = LinearTripleCollector.
                                                createCollector(datasetURI).
                                                getTriples();

        CollisionRate cr = new CollisionRate(triples, "Triple Hashcode Collision Rate");
        HashSetCollisions hsc = new HashSetCollisions(triples, "Java HashSet Collision Rate");
        JenaHashBunchCollisionRate jhbc = new JenaHashBunchCollisionRate(triples, "Jena HashBunch");
        HashSetAndrewOma hsao = new HashSetAndrewOma(triples, "Andrew Oma HashSet");
        HashMultisetCollisionRate hmcr = new HashMultisetCollisionRate(triples, "JSON-LD HashMultiset");
        EvaluationLogger logger = new EvaluationLogger(List.of(cr, hsc, jhbc, hsao, hmcr));
        logger.computeAndLog();
    }
}
