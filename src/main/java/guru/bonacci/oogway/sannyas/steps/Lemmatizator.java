package guru.bonacci.oogway.sannyas.steps;

import static java.util.stream.Collectors.joining;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/*
 * Lemmatisation (or lemmatization) in linguistics is the process of grouping together 
 * the inflected forms of a word so they can be analysed as a single item, identified 
 * by the word's lemma, or dictionary form.
 * 
 * In computational linguistics, lemmatisation is the algorithmic process of determining 
 * the lemma of a word based on its intended meaning. Unlike stemming, lemmatisation 
 * depends on correctly identifying the intended part of speech and meaning of a word 
 * in a sentence, as well as within the larger context surrounding that sentence, such as 
 * neighboring sentences or even an entire document.
 * 
 * 
 * This is a pipeline that takes in a string and returns various analyzed linguistic forms.
 * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator),
 * and then other sequence model style annotation can be used to add things like lemmas,
 * POS tags, and named entities. These are returned as a list of CoreLabels.
 * Other analysis components build and store parse trees, dependency graphs, etc.
 */
@Component
public class Lemmatizator implements Function<String,String> {

  private StanfordCoreNLP pipeline;

  public Lemmatizator() {
    // Create StanfordCoreNLP object properties, with POS tagging
    Properties props= new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");
    this.pipeline = new StanfordCoreNLP(props);
  }

  @Override
  public String apply(String documentText) {
    List<String> lemmas = new LinkedList<>();
    // Create an empty Annotation just with the given text
    Annotation document = new Annotation(documentText);
    // run all Annotators on this text
    this.pipeline.annotate(document);
    // Iterate over all of the sentences found
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for(CoreMap sentence: sentences) {
      // Iterate over all tokens in a sentence
      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
        // Retrieve and add the lemma for each word into the list of lemmas
        lemmas.add(token.get(LemmaAnnotation.class));
      }
    }
    return lemmas.stream().collect(joining(" "));
  }

  //TODO use for testing
  public static void main(String[] args) {
    System.out.println("Starting Stanford Lemmatizer");
    String text = "How could you be seeing into my eyes like open doors? \n"+
        "You led me down into my core where I've became so numb \n"+
        "Without a soul my spirit's sleeping somewhere cold \n"+
        "Until you find it there and led it back home \n"+
        "You woke me up inside \n"+
        "Called my name and saved me from the dark \n"+
        "You have bidden my blood and it ran \n"+
        "Before I would become undone \n"+
        "You saved me from the nothing I've almost become \n"+
        "You were bringing me to life \n"+
        "Now that I knew what I'm without \n"+
        "You can've just left me \n"+
        "You breathed into me and made me real \n"+
        "Frozen inside without your touch \n"+
        "Without your love, darling \n"+
        "Only you are the life among the dead \n"+
        "I've been living a lie, there's nothing inside \n"+
        "You were bringing me to life.";
    Lemmatizator slem = new Lemmatizator();
    System.out.println(slem.apply(text));
  }
}
