import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticServices {
    public static void main(String[] args) throws IOException {
        System.out.println(getSuggestion("Ha"));
    }

    public static List<String> getSuggestion(String input) throws IOException {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("172.17.80.25:9200").build();

        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        // create a search source builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // create a suggest builder
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        // create a term suggestion builder
        SuggestionBuilder<CompletionSuggestionBuilder> termSuggestionBuilder = SuggestBuilders.completionSuggestion("suggest_title").text("Ha").skipDuplicates(true);
        // add the term suggestion builder to the suggest builder
        suggestBuilder.addSuggestion("title-suggest", termSuggestionBuilder);
        // add the suggest builder to the search source builder
        searchSourceBuilder.suggest(suggestBuilder);

        // create a search request
        SearchRequest searchRequest = new SearchRequest("title_suggest_hoangnlv");
        // set the search source builder to the search request
        searchRequest.source(searchSourceBuilder);

        // get the search response
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // get the suggestions
        Suggest suggest = searchResponse.getSuggest();
        // get the suggestions from the suggest
        List<Suggest.Suggestion.Entry.Option> options = (List<Suggest.Suggestion.Entry.Option>) suggest.getSuggestion("title-suggest").getEntries().get(0).getOptions();

        // get the text from the options
        return options.stream().map(option -> option.getText().toString()).collect(Collectors.toList());
    }
}