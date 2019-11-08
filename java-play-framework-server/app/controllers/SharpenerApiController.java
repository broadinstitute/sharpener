package controllers;

import apimodels.AggregationQuery;
import apimodels.ErrorMsg;
import apimodels.GeneList;
import java.util.List;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import java.io.File;
import swagger.SwaggerUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.validation.constraints.*;
import play.Configuration;

import swagger.SwaggerUtils.ApiAction;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-08T23:25:48.604Z")

public class SharpenerApiController extends Controller {

    private final SharpenerApiControllerImpInterface imp;
    private final ObjectMapper mapper;
    private final Configuration configuration;

    @Inject
    private SharpenerApiController(Configuration configuration, SharpenerApiControllerImpInterface imp) {
        this.imp = imp;
        mapper = new ObjectMapper();
        this.configuration = configuration;
    }


    @ApiAction
    public Result aggregatePost() throws Exception {
        JsonNode nodequery = request().body().asJson();
        AggregationQuery query;
        if (nodequery != null) {
            query = mapper.readValue(nodequery.toString(), AggregationQuery.class);
            if (configuration.getBoolean("useInputBeanValidation")) {
                SwaggerUtils.validate(query);
            }
        } else {
            throw new IllegalArgumentException("'query' parameter is required");
        }
        GeneList obj = imp.aggregatePost(query);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result createGeneListPost() throws Exception {
        JsonNode nodequery = request().body().asJson();
        List<String> query;
        if (nodequery != null) {
            query = mapper.readValue(nodequery.toString(), new TypeReference<List<String>>(){});
            if (configuration.getBoolean("useInputBeanValidation")) {
                for ( curItem : query) {
                    SwaggerUtils.validate(curItem);
                }
            }
        } else {
            throw new IllegalArgumentException("'query' parameter is required");
        }
        GeneList obj = imp.createGeneListPost(query);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result geneListGeneListIdGet(String geneListId) throws Exception {
        GeneList obj = imp.geneListGeneListIdGet(geneListId);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result transformPost() throws Exception {
        JsonNode nodequery = request().body().asJson();
        TransformerQuery query;
        if (nodequery != null) {
            query = mapper.readValue(nodequery.toString(), TransformerQuery.class);
            if (configuration.getBoolean("useInputBeanValidation")) {
                SwaggerUtils.validate(query);
            }
        } else {
            throw new IllegalArgumentException("'query' parameter is required");
        }
        GeneList obj = imp.transformPost(query);
        if (configuration.getBoolean("useOutputBeanValidation")) {
            SwaggerUtils.validate(obj);
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result transformersGet() throws Exception {
        List<TransformerInfo> obj = imp.transformersGet();
        if (configuration.getBoolean("useOutputBeanValidation")) {
            for (TransformerInfo curItem : obj) {
                SwaggerUtils.validate(curItem);
            }
        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }
}
