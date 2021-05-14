package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.common.BizException;
import com.demo.esVo.SkuInfo;
import com.demo.interfaceService.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1.时间格式的转换
 * 2.分页总数获取错误
 */

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private JestClient jestClient;

    //第二种连接方法与在ymi配置一样
    public JestClient getJestCline(){
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://127.0.0.1:9200")
                .multiThreaded(true)
                .build());
        return  factory.getObject();
    }


    public List<SkuInfo> search () {

        //参考kibana写出来的语句，一样一样的去塞
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //在查询下建立一个bool
        BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
        //bool下的must，下的match查询，是只含有还是必须都有
        booleanQueryBuilder.must(QueryBuilders.matchQuery("skuName", "手机").operator(Operator.AND));
        //boo下的filter，筛选，必须相等
        booleanQueryBuilder.filter(QueryBuilders.termQuery("catalog3Id", "202"));
        searchSourceBuilder.query(booleanQueryBuilder);

        //es的分页，与query平级
        searchSourceBuilder.from(0);   //表示起始行（从0开始）
        searchSourceBuilder.size(10);   //表示每页的数量

        //高亮，与query平级
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("skuName").preTags("<span>").postTags("</spam>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //聚合，与query平级
        searchSourceBuilder.aggregation(AggregationBuilders.terms("group_by_catalog3Id").field("catalog3Id").size(10));

        //排序，与query平级
        searchSourceBuilder.sort("catalog3Id", SortOrder.DESC);

        //创建访问具体的index
        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString());
        Search search = searchBuilder.addIndex("test_index").build();
        try {
            //得到返回值
            //1.用配置连接
            //SearchResult searchResult = jestClient.execute(search);
            //2.用代码连接
            SearchResult searchResult = getJestCline().execute(search);
            List<SkuInfo> skuInfoList = new ArrayList<>();
            List<SearchResult.Hit<SkuInfo, Void>> hits = searchResult.getHits(SkuInfo.class);
            for(SearchResult.Hit<SkuInfo, Void> hit:hits) {
                SkuInfo skuInfo = hit.source;
                skuInfoList.add(skuInfo);
                //取出高亮部分
                Map<String, List<String>> map = hit.highlight;
                for (String key:map.keySet()) {
                    System.out.println("key为：" + key);
                    for (String value:map.get(key)) {
                        System.out.println(value);
                    }
                }
                System.out.println("-------------------------------------------------");
            }

            //总数  ---得到总数和当前页码很容易计算其余值
//            Long total = searchResult.getTotal();
//            System.out.println("查询出的总数量:" + total.toString());

            //取出聚合的值
            List<TermsAggregation.Entry> termsAggregationList = searchResult.getAggregations().getTermsAggregation("group_by_catalog3Id").getBuckets();
            for (TermsAggregation.Entry termsAggregation:termsAggregationList) {
                System.out.println("key为：" + termsAggregation.getKey() + "，value为:" + termsAggregation.getCount());
            }
            return skuInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail("连接访问es错误");
        }
        return null;
    }
}
