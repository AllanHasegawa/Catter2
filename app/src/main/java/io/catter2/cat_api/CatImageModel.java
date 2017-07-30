package io.catter2.cat_api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image")
public class CatImageModel {
    @Element(name = "id")
    public String id;

    @Element(name = "url")
    public String url;

    @Element(name = "source_url")
    public String sourceUrl;
}
