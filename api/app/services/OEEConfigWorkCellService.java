package services;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;
import utils.RedisClient;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static play.libs.F.Promise.promise;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.noContent;
import static play.mvc.Results.ok;

/**
 * Created by nfaure on 5/21/16.
 */
@Slf4j
public class OEEConfigWorkCellService {


    public static final String OEE_CONFIG_WORK_CELL_KEY = "oeeconfigworkcell:";
    public static final String OEE_IDS_KEY = "oee:ids";
    @Autowired
    private RedisClient redisClient;


    public F.Promise<List<String>> getAll(){
        return promise(new F.Function0<List<String>>() {
            @Override
            public List<String> apply() throws Throwable {
                    Set<String> oeeKeys = redisClient.getAllKeysMatching(format("%s*", OEE_CONFIG_WORK_CELL_KEY));
                    return redisClient.mget(oeeKeys);
            }
        });
    }

    public F.Promise<Result> create(final JsonNode oeeConfigWorkCell) {
        return promise(new F.Function0<Result>(){

            @Override
            public Result apply() throws Throwable {
                String id = redisClient.incr(OEE_IDS_KEY).toString();
                String lineId = oeeConfigWorkCell.get("line_id").asText();
                String type = oeeConfigWorkCell.get("type").asText();
                String scheduleId = oeeConfigWorkCell.get("schedule_id").asText();
                String scheduleValueType = oeeConfigWorkCell.get("schedule_value_type").asText();
                String description = oeeConfigWorkCell.get("description").asText();
                String idealCycleTime = oeeConfigWorkCell.get("ideal_cycle_time").asText();
                String defaultRsSqlId = oeeConfigWorkCell.get("default_rs_sql_id").asText();
                String partId = oeeConfigWorkCell.get("part_id").asText();
                String partIdRsSqlDataPointId = oeeConfigWorkCell.get("part_id").asText();
                String partIdFlags = oeeConfigWorkCell.get("part_id_flags").asText();
                String partCntRsSqlId = oeeConfigWorkCell.get("part_cnt_rs_sql_id").asText();
                return null;

            }
        });
    }
}
