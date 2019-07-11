package controllers;

import apimodels.AggregationQuery;
import apimodels.GeneList;
import java.util.List;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-07-11T16:00:13.997Z")

public class SharpenerApiControllerImp implements SharpenerApiControllerImpInterface {
    @Override
    public GeneList aggregatePost(AggregationQuery query) throws Exception {
        //Do your magic!!!
        return new GeneList();
    }

    @Override
    public GeneList createGeneListPost(List<String> query) throws Exception {
        //Do your magic!!!
        return new GeneList();
    }

    @Override
    public GeneList transformPost(TransformerQuery query) throws Exception {
        //Do your magic!!!
        return new GeneList();
    }

    @Override
    public List<TransformerInfo> transformersGet() throws Exception {
        //Do your magic!!!
        return new ArrayList<TransformerInfo>();
    }

}
