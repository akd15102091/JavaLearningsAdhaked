package PullBasedMessageQueue;

public class Message<T> {
    private final T data;
    private final Long expiryAt;

    public Message(T data, Long ttlMillis) {
        this.data = data;
        this.expiryAt = (ttlMillis == null) ? null :
                System.currentTimeMillis() + ttlMillis;
    }

    public boolean isExpired(long now) {
        return expiryAt != null && now >= expiryAt;
    }

    public T getData() {
        return data;
    }
}
