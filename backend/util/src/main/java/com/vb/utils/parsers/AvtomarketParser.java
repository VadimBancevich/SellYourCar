package com.vb.utils.parsers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.vb.api.dao.IBrandDao;
import com.vb.api.dao.IGenerationDao;
import com.vb.api.dao.IModelDao;
import com.vb.entities.Brand;
import com.vb.entities.Generation;
import com.vb.entities.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvtomarketParser {

    @Autowired
    private IBrandDao brandDao;

    @Autowired
    private IModelDao modelDao;

    @Autowired
    private IGenerationDao generationDao;

    private final WebClient webClient = WebClientBuilder.getWebClient();

    private static final String BRANDS_URL = "http://avtomarket.ru/catalog";

    private static final String BASE_URL = "http://avtomarket.ru";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void parseBrand() {
        try {
            final HtmlPage allBrandsPage = webClient.getPage(BRANDS_URL);

            List<HtmlElement> allBrands = allBrandsPage.getByXPath("//ul[@id=\"name-list\"]/li/a").stream().map(o -> (HtmlElement) o).collect(Collectors.toList());

            allBrands.stream().forEach(htmlElement -> {
                String brandName = htmlElement.getTextContent();
                Brand brand = brandDao.findByBrandName(brandName).orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setBrandName(brandName);
                    return brandDao.save(newBrand);
                });
                parseModel(brand, htmlElement.getAttribute("href"));
            });
        } catch (Exception e) {
            logger.error(String.format("Exception during open page with brands %s", e.getMessage()));
        }
    }

    public void parseModel(Brand brand, String brandUrl) {
        try {
            final HtmlPage allModelsOfBrandPAge = webClient.getPage(BASE_URL + brandUrl);

            List<HtmlElement> allModels = allModelsOfBrandPAge.getByXPath("//ul[@id=\"name-list\"]/li/a").stream().map(o -> (HtmlElement) o).collect(Collectors.toList());

            allModels.forEach(htmlElement -> {
                String modelName = htmlElement.getTextContent().replaceAll(brand.getBrandName() + "\\s*", "");
                Model model = modelDao.findByModelNameAndBrand_Id(modelName, brand.getId()).orElseGet(() -> {
                    Model newModel = new Model();
                    newModel.setBrand(brand);
                    newModel.setModelName(modelName);
                    return modelDao.save(newModel);
                });
                parseGeneration(model, htmlElement.getAttribute("href"));
            });
        } catch (Exception e) {
            logger.error(String.format("Exception during open page with models %s", e.getMessage()));
        }
    }

    public void parseGeneration(Model model, String modelUrl) {
        try {
            final HtmlPage allGenerationsOfModelPage = webClient.getPage(BASE_URL + modelUrl);

            List<HtmlElement> allGenerations = allGenerationsOfModelPage.getByXPath("//ul[@class=\"groupx\"]/li").stream().map(o -> (HtmlElement) o).collect(Collectors.toList());

            allGenerations.forEach(htmlElement -> {

                HtmlElement generationNameElement = (HtmlElement) htmlElement.getFirstElementChild().getNextElementSibling().getNextElementSibling().getFirstElementChild();
                HtmlElement yearsProductionElement = (HtmlElement) generationNameElement.getParentNode().getNextElementSibling();

                String generationName = generationNameElement.getTextContent().replaceAll(".*" + model.getModelName() + "\\s*", "");
                String generationYears = yearsProductionElement.getTextContent().replaceAll("[а-яА-Я]", "");

                String generationNameAndYears = (generationName + " " + generationYears).trim();

                if (!generationDao.existsByGenerationNameAndModel_Id(generationNameAndYears, model.getId())) {
                    Generation newGeneration = new Generation();
                    newGeneration.setModel(model);
                    newGeneration.setGenerationName(generationNameAndYears);
                    generationDao.save(newGeneration);
                }
            });
        } catch (Exception e) {
            logger.error(String.format("Exception during open page with generations %s", e.getMessage()));
        }
    }
}
