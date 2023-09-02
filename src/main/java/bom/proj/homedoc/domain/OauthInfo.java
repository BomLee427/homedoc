package bom.proj.homedoc.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class OauthInfo {

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    private String oauthId;

    public static OauthInfo of(OauthType oauthType, String oauthId) {
        OauthInfo oauthInfo = new OauthInfo();
        oauthInfo.oauthType = oauthType;
        oauthInfo.oauthId = oauthId;

        return oauthInfo;
    }
}
