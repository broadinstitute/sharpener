package controllers;

import apimodels.AggregationQuery;
import apimodels.GeneList;
import java.util.List;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

import play.mvc.Http;
import sharpener.GeneLists;
import sharpener.Transformers;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-12T19:29:34.374Z")

public class SharpenerApiControllerImp implements SharpenerApiControllerImpInterface {
    @Override
    public GeneList aggregatePost(AggregationQuery query) throws Exception {
    	return GeneLists.aggregate(query);
    }

    @Override
    public GeneList createGeneListPost(List<String> query) throws Exception {
    	return GeneLists.createList(query);
    }

    @Override
    public GeneList geneListGeneListIdGet(String geneListId) throws Exception {
        //Do your magic!!!
        return new GeneList();
    }

    @Override
    public GeneList transformPost(TransformerQuery query) throws Exception {
    	return Transformers.transform(query);
    }

    @Override
    public List<TransformerInfo> transformersGet() throws Exception {
        return Transformers.getTransformers();
    }

}
