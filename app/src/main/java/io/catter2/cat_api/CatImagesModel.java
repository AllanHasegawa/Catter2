package io.catter2.cat_api;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "response")
public class CatImagesModel {
    @ElementList(inline = true)
    @Path("data/images")
    public List<CatImageModel> catImages;
}
