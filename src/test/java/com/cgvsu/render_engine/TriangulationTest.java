package com.cgvsu.render_engine;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TriangulationTest {

    @Test
    void triangulate() {
        Model assertFromModel = new Model();
        assertFromModel.vertices.add(new Vector3f(1F, 1F, 1F));
        assertFromModel.vertices.add(new Vector3f(1F, 2F, 1F));
        assertFromModel.vertices.add(new Vector3f(2F, 2F, 1F));
        assertFromModel.vertices.add(new Vector3f(2F, 1F, 1F));
        assertFromModel.normals.add(new Vector3f(1F, 1F, 1F));
        assertFromModel.normals.add(new Vector3f(1F, 2F, 1F));
        assertFromModel.normals.add(new Vector3f(2F, 2F, 1F));
        assertFromModel.normals.add(new Vector3f(2F, 1F, 1F));
        assertFromModel.textureVertices.add(new Vector2f(1f, 2f));


        Polygon polygon = new Polygon();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);list.add(1);list.add(2);list.add(3);
        polygon.setNormalIndices(list);
        polygon.setTextureVertexIndices(list);
        polygon.setVertexIndices(list);

        assertFromModel.polygons.add(polygon);

        Model assertToModel = new Model();
        assertToModel.vertices.add(new Vector3f(1F, 1F, 1F));
        assertToModel.vertices.add(new Vector3f(1F, 2F, 1F));
        assertToModel.vertices.add(new Vector3f(2F, 2F, 1F));
        assertToModel.vertices.add(new Vector3f(2F, 1F, 1F));
        assertToModel.normals.add(new Vector3f(1F, 1F, 1F));
        assertToModel.normals.add(new Vector3f(1F, 2F, 1F));
        assertToModel.normals.add(new Vector3f(2F, 2F, 1F));
        assertToModel.normals.add(new Vector3f(2F, 1F, 1F));
        assertToModel.textureVertices.add(new Vector2f(1f, 2f));


        Polygon polygon2 = new Polygon();
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(0);list2.add(1);list2.add(2);list2.add(3);
        polygon2.setNormalIndices(list2);
        polygon2.setTextureVertexIndices(list2);
        polygon2.setVertexIndices(list2);

        assertToModel.polygons.add(polygon2);
    }
}