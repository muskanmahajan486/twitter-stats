package no.dervis.gts.database;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TwitterStatDb implements Serializable {

    private static final long serialVersionUID = 9071006316792724364L;

    private List<SnapShot> data;

    public TwitterStatDb() {
        this.data = new LinkedList<>();
    }

    public TwitterStatDb(List<SnapShot> data) {
        this.data = data;
    }

    public TwitterStatDb addSnapShot(SnapShot snapShot) {
        data.add(snapShot);
        return this;
    }

    public List<SnapShot> getData() {
        return data;
    }

    /**
     * Sorts the snapshots from earliest to latest date.
     * @return This instance.
     */
    public TwitterStatDb sortData() {
        return new TwitterStatDb(data.stream().sorted(SnapShot::compareTo).collect(Collectors.toList()));
    }

    /**
     * Reverse the order of the elements.
     * @return The reversed list.
     */
    public TwitterStatDb reverseOrder() {
        List<SnapShot> reversed = new LinkedList<>(data);
        Collections.reverse(reversed);
        return new TwitterStatDb(reversed);
    }

    public Optional<SnapShot> first() {
        if (data.isEmpty()) return Optional.empty();
        return Optional.of(data.get(0));
    }

    public Optional<SnapShot> last() {
        if (data.isEmpty()) return Optional.empty();
        if (data.size() == 1) return Optional.of(data.get(0));

        return data.stream().skip(data.size()-1).findFirst();
    }

    @Override
    public String toString() {
        return "TwitterStatDb{" +
                "data=" + data +
                '}';
    }
}
