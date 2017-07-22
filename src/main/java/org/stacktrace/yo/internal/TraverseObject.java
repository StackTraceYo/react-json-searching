package org.stacktrace.yo.internal;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TraverseObject {

    private List<String> traverseList;
    private String searchWord = "";
    private String path = "";

    public TraverseObject() {
        traverseList = new ArrayList<>();
    }

    public List<String> getTraverseList() {
        return traverseList;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public TraverseObject setSearchWord(String searchWord) {
        this.searchWord = searchWord;
        return this;
    }

    public TraverseObject setTraverseList(List<String> traverseList) {
        this.traverseList = traverseList;
        this.path = traverseList.stream().collect(Collectors.joining("."));
        return this;

    }

    public boolean needsTraversal() {
        return traverseList.size() >= 1;
    }

    public boolean hasSearchWord() {
        return !StringUtils.isEmpty(searchWord);
    }

    public String getPath() {
        return path;
    }

    public TraverseObject setPath(String path) {
        this.path = path;
        return this;
    }
}