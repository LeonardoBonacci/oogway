package guru.bonacci.spectre.sentiment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import guru.bonacci.spectre.sentiment.es.Spec;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SentimentService implements SpectreService {

	
	@Autowired
	private ElasticAdapter repo;

	@Autowired
	private StanfordCoreNLP pipeline;
	
	
	public Mono<String> enrich(final String id) {
		log.info("processing " + id);

		return repo.findById(id)
			.map(Spec::getMessage)
			.flatMap(this::findSentimentDesc)
			.flatMap(desc -> repo.update(id, "sentiment", desc).then(Mono.just(desc)))
			.onErrorReturn("no sentiment");
	}

	Mono<String> findSentimentDesc(String line) {
		return Mono.just(toCss(findSentiment(line)));
	}

	Integer findSentiment(String line) {
		log.debug("in: " + line);

		int mainSentiment = 0;
		if (line != null && line.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(line);
			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}

			}
		}

		log.debug("out: " + mainSentiment);
		return mainSentiment;
	}
	
	private String toCss(int sentiment) {
        switch (sentiment) {
        case 0:
            return "very negative";
        case 1:
            return "negative";
        case 2:
            return "neutral";
        case 3:
            return "positive";
        case 4:
            return "very positive";
        default:
            return "default";
        }
     }
}
