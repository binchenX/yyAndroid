package com.pierr.rockyouth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierr on 13-9-14.
 *
 * This is the Model use to query the data from the server or local cache
 *
 */
public class AlbumDataBase {

    public static final int SORT_BY_TIME = 1;
    public static final int SORT_BY_RATING = 2;

    public static List<Album> queryAlbum(int condition)
    {
        ArrayList<Album> albums = new ArrayList<Album>();

        if (condition == SORT_BY_TIME) {
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
            albums.add(new Album("douwei", "title", "imageUrl", 5));
        } else if (condition == SORT_BY_RATING){
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));
            albums.add(new Album("zhangchu", "title2", "imageUrl", 5));

        }

        return albums;

    }
}
