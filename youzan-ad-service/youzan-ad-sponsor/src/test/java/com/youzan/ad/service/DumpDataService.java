package com.youzan.ad.service;

import com.alibaba.fastjson.JSON;
import com.youzan.ad.Application;
import com.youzan.ad.constant.CommonStatus;
import com.youzan.ad.dao.AdCreativeRepository;
import com.youzan.ad.dao.AdPlanRepository;
import com.youzan.ad.dao.AdUnitRepository;
import com.youzan.ad.dao.unit_condition.AdCreativeUnitRepository;
import com.youzan.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.youzan.ad.dao.unit_condition.AdUnitItRepository;
import com.youzan.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.youzan.ad.dump.DConstant;
import com.youzan.ad.dump.table.*;
import com.youzan.ad.entity.AdCreative;
import com.youzan.ad.entity.AdPlan;
import com.youzan.ad.entity.AdUnit;
import com.youzan.ad.entity.unit_condition.AdCreativeUnit;
import com.youzan.ad.entity.unit_condition.AdUnitDistrict;
import com.youzan.ad.entity.unit_condition.AdUnitIt;
import com.youzan.ad.entity.unit_condition.AdUnitKeyword;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baimugudu on 2019/4/3
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private AdCreativeRepository adCreativeRepository;
    @Autowired
    private AdCreativeUnitRepository adCreativeUnitRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;




    @Test
    public void dumpAdTableData() throws IOException {
       dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR,
                DConstant.AD_PLAN));


        dumpUnitTable(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_UNIT));

        dumpCreativeTable(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_CREATIVE));

        dumpCreativeUnitTable(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_CREATIVE_UNIT));

        dumpCreativeDistrictTable(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_UNIT_DISTRICT));

        dumpCreativeItTable(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_UNIT_IT));

        dumpCreativeKeyWord(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                DConstant.AD_UNIT_KEYWORD));



    }



    private void dumpAdPlanTable(String fileName) throws IOException {
        List<AdPlan> adPlan = adPlanRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );
        ArrayList<AdPlanTable> adPlanTables = new ArrayList<>();
        adPlan.forEach(
                i->adPlanTables.add(
                        new AdPlanTable(
                              i.getId(),

                                i.getUserId(),
                                i.getPlanStatus(),
                                i.getStartTime(),
                                i.getEndTime()
                        )
                )

        );
        Path path = Paths.get(fileName);

        try ( BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            for (AdPlanTable adPlanTable : adPlanTables) {
                bufferedWriter.write(JSON.toJSONString(adPlanTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }catch (IOException ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }



    }


    private void dumpUnitTable(String fileName){
       List<AdUnit> adUnits =  adUnitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus()
        );
       List<AdUnitTable> adUnitTables = new ArrayList<>();

       adUnits.forEach(
                i-> adUnitTables.add(
                        new AdUnitTable(
                                i.getPlanId(),
                                i.getId(),
                                i.getUnitStatus(),
                                i.getPositionType()
                        )
                )
        );

       Path path = Paths.get(fileName);
       try(BufferedWriter writer = Files.newBufferedWriter(path)){
           for(AdUnitTable adUnitTable:adUnitTables){
               writer.write(JSON.toJSONString(adUnitTable));
               writer.newLine();
           }
           writer.close();
       }catch(IOException e){
           e.printStackTrace();
           log.error(e.getMessage());
        }

    }


    private void dumpCreativeTable(String fileName){
        List<AdCreative> creativeList = adCreativeRepository.findAll();
        List<AdCreativeTable> adCreativeTbales = new ArrayList<>();

        creativeList.forEach(
                c->adCreativeTbales.add(
                        new AdCreativeTable(
                              c.getId(),
                                c.getName(),
                                c.getType(),
                                c.getMaterialType(),
                                c.getHeight(),
                                c.getWidth(),
                                c.getAuditStatus(),
                                c.getUrl()
                        )
                )
        );

        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdCreativeTable adCreativeTbale:adCreativeTbales){
                writer.write(JSON.toJSONString(adCreativeTbale));
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
            log.error(e.getMessage());
        }




    }


    private void dumpCreativeUnitTable(String fileName){

        List<AdCreativeUnit> adCreativeUnits = adCreativeUnitRepository.findAll();
        List<AdCreativeUnitTable> adCreativeUnitTbales = new ArrayList<>();
        adCreativeUnits.forEach(
                ad->adCreativeUnitTbales.add(
                        new AdCreativeUnitTable(
                                ad.getUnitId(),
                                ad.getCreativeId()
                        )
                )
        );


       Path path =  Paths.get(fileName);

       try(BufferedWriter writer = Files.newBufferedWriter(path)){
           for(AdCreativeUnitTable adCreativeUnitTbale:adCreativeUnitTbales){
               writer.write(JSON.toJSONString(adCreativeUnitTbale));
               writer.newLine();
           }

           writer.close();


       }catch(IOException e){
           e.printStackTrace();
        }


    }


    private void dumpCreativeDistrictTable(String fileName){

         List<AdUnitDistrict> adUnitDistricts = adUnitDistrictRepository.findAll();
         List<AdUnitDistrictTable> adUnitDistrictTables = new ArrayList<>();

        adUnitDistricts.forEach(
                i->adUnitDistrictTables.add(
                        new AdUnitDistrictTable(
                                i.getUnitId(),
                                i.getProvince(),
                                i.getCity()

                        )
                )
        );

        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdUnitDistrictTable adUnitDistrictTable:adUnitDistrictTables){
                writer.write(JSON.toJSONString(adUnitDistrictTable));
                writer.newLine();
            }
            writer.close();

        }catch(IOException e){
            e.printStackTrace();
        }



    }




    private void dumpCreativeItTable(String fileName){

        List<AdUnitIt> adUnitIts = adUnitItRepository.findAll();
        List<AdUnitItTable> adUnitItTables = new ArrayList<>();

        adUnitIts.forEach(
                i->adUnitItTables.add(
                        new AdUnitItTable(
                                i.getUnitId(),
                                i.getItTag()
                        )

                        )

        );

        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdUnitItTable adUnitItTable:adUnitItTables){
                writer.write(JSON.toJSONString(adUnitItTable));
                writer.newLine();
            }
            writer.close();

        }catch(IOException e){
            e.printStackTrace();
        }



    }



    private void dumpCreativeKeyWord(String fileName) throws IOException {
  /*      List<AdUnitKeyword> adUnitKeywords = adUnitKeywordRepository.findAll();
        ArrayList<AdUnitKeyword> unitKeywordTables = new ArrayList<>();
        adUnitKeywords.forEach(
                i->unitKeywordTables.add(
                        new AdUnitKeywordTable(
                                i.getUnitId(),
                                i.getKeyword()
                        )

                )
        );*/
        List<AdUnitKeyword> unitKeywords = adUnitKeywordRepository.findAll();
        ArrayList<AdUnitKeyword> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(
              i->  new AdUnitKeywordTable(
                      i.getUnitId(),
                      i.getKeyword()
              )

        );
        Path path = Paths.get(fileName);

        try (  BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            for (AdUnitKeyword unitKeywordTable : unitKeywordTables) {
                bufferedWriter.write(JSON.toJSONString(unitKeywordTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
       /* List<AdUnitKeyword> adUnitKeywords = adUnitKeywordRepository.findAll();
        List<AdUnitKeywordTable> adUnitItTables = new ArrayList<>();

        adUnitKeywords.forEach(
                i->adUnitItTables.add(
                        new AdUnitKeywordTable(
                               i.getUnitId(),
                                i.getKeyword()
                        )

                )

        );

        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for(AdUnitKeywordTable adUnitItTable:adUnitItTables){
                writer.write(JSON.toJSONString(adUnitItTable));
                writer.newLine();
            }
            writer.close();

        }catch(IOException e){
            e.printStackTrace();
        }*/



    }





}
