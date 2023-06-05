package us.hcheng.javaio.learnhspedu.projects.property_app.service;

import us.hcheng.javaio.learnhspedu.projects.property_app.entity.House;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class PropertyService {

    private House[] arr;
    private int counter;
    private int idx;


    public PropertyService() {
        arr = new House[10];
        idx = 0;
    }

    public void createListing(House h) {
        h.setId(counter++);
        arr[idx++] = h;
        resizeWhenNecessary();
    }

    public House retrieveListing(int id) {
        return Arrays.stream(this.arr).filter(house -> house != null && house.getId() == id)
                .findFirst().orElse(null);
    }

    private int getListingIdx(int id) {
        for (int i = 0; i < idx; i++)
            if (id == arr[i].getId())
                return i;
        return -1;
    }

    public Stream<House> getAllListings() {
        return Arrays.stream(this.arr).filter(Objects::nonNull);
    }

    public boolean deleteListing(int id) {
        int idx = this.getListingIdx(id);
        if (idx == -1)
            return false;
        swap(id, --idx);
        arr[idx] = null;
        return true;
    }

    private void swap(int i, int j) {
        if (i == j)
            return;

        House tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void setNewArr(House[] newArr) {
        System.arraycopy(arr, 0, newArr, 0, idx);
        arr = newArr;
    }

    private void resizeWhenNecessary() {
        int len = arr.length;
        if (idx * 2 >= len)
            setNewArr(new House[len * 2]);
        else if (len > 40 && len / 4 > idx)
            setNewArr(new House[len / 4]);
    }

}
