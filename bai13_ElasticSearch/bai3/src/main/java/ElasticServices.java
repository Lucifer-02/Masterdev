import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.IOException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ElasticServices{
    public static void main(String[] args) throws IOException {
        System.out.println(getSuggestion("Ha"));
    }

    public static List<String> getSuggestion(String input) throws IOException {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("172.17.80.25:9200").build();

        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.completionSuggestion("suggest_title").text(input).skipDuplicates(true);
//        SuggestionBuilder termSuggestionBuilder1 = SuggestBuilders.completionSuggestion("suggest_title").text("Ha").skipDuplicates(true);

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("title-suggest", termSuggestionBuilder);
//        suggestBuilder.addSuggestion("title-suggest1", termSuggestionBuilder1);


        searchSourceBuilder.suggest(suggestBuilder);

        SearchRequest searchRequest = new SearchRequest("title_suggest_hoangnlv");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(searchResponse.getSuggest().iterator(), Spliterator.ORDERED), false)
                .flatMap(suggestion -> suggestion.getEntries().get(0).getOptions().stream())
                .map((Suggest.Suggestion.Entry.Option option) -> option.getText().toString())
                .collect(Collectors.toList());
    }
}