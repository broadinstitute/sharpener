package controllers;

import apimodels.AggregationQuery;
import apimodels.ErrorMsg;
import apimodels.GeneList;
import java.util.List;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.*;

@SuppressWarnings("RedundantThrows")
public interface SharpenerApiControllerImpInterface {
    GeneList aggregatePost(AggregationQuery query) throws Exception;

    GeneList createGeneListPost(List<String> query) throws Exception;

    GeneList geneListGeneListIdGet(String geneListId) throws Exception;

    GeneList transformPost(TransformerQuery query) throws Exception;

    List<TransformerInfo> transformersGet() throws Exception;

}
