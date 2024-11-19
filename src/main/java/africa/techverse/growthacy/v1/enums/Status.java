package africa.techverse.growthacy.v1.enums;


import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(5), INACTIVE(10);

    private final int value;
    
    Status(int value) {
        this.value = value;
    }
}