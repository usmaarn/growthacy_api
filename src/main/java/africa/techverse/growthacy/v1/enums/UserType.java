package africa.techverse.growthacy.v1.enums;

import lombok.Getter;

@Getter
public enum UserType {
    ADMIN(5), COMPANY(10), AMBASSADOR(20);

    private final int value;

    UserType(int value) {
        this.value = value;
    }
}
