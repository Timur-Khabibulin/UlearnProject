package org.example.vk;

import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VkApi {
    private final VkApiClient vk;
    private final ServiceActor serviceActor;

    VkApi(@Value("${vk.api.app-id}") Integer appId,
          @Value("${vk.api.service-key}") String clientSecret,
          @Value("${vk.api.access-token}") String accessToken) {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        serviceActor = new ServiceActor(appId, clientSecret, accessToken);
    }

    public Result<List<String>> getGroupMembers(String groupId) {
        return Result.callAndGet(() -> vk.groups()
                .getMembers(serviceActor)
                .groupId(groupId)
                .execute()
                .getItems()
                .stream().map(Objects::toString)
                .toList()
        );
    }

    public Result<List<GetResponse>> getUsers(List<String> userIds) {
        return Result.callAndGet(() -> vk.users()
                .get(serviceActor)
                .userIds(userIds)
                .fields(Fields.COUNTRY,
                        Fields.CITY,
                        Fields.BDATE,
                        Fields.SEX,
                        Fields.SCHOOLS
                )
                .lang(Lang.RU)
                .execute()
        );
    }
}
