package com.cgvsu.render_engine;

import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class Triangulation {
    public static Model triangulate(Model model) {
        Model newModel = new Model();
        newModel.textureVertices = model.textureVertices;
        newModel.normals = model.normals;
        newModel.vertices = model.vertices;

        for (int i = 0; i < model.polygons.size(); i++) { //пробегаем все полигоны
            Polygon currPolygon = model.polygons.get(i);
            int vCount = currPolygon.getVertexIndices().size();

            if (vCount > 3) {
                for (int j = 2; j < vCount; j++) {
                    newModel.polygons.add(createNewPolygon(0, j - 1, j, currPolygon));
                }
            }
            else {
                newModel.polygons.add(currPolygon);
            }
        }
        return newModel;
    }
    private static Polygon createNewPolygon(int v1, int v2, int v3, Polygon originPolygon) {
        Polygon newPolygon = new Polygon();
        //Нормали

        if (originPolygon.getNormalIndices().size() > 3) {
            ArrayList<Integer> listOfNorlmal = new ArrayList<>();
            listOfNorlmal.add(originPolygon.getNormalIndices().get(v1));
            listOfNorlmal.add(originPolygon.getNormalIndices().get(v2));
            listOfNorlmal.add(originPolygon.getNormalIndices().get(v3));
            newPolygon.setNormalIndices(listOfNorlmal);
        }

        newPolygon.setNormalIndices(originPolygon.getNormalIndices());
        //Текстурные вершины

        if (originPolygon.getTextureVertexIndices().size() > 3) {
            ArrayList<Integer> listOfTextureVertex = new ArrayList<>();
            listOfTextureVertex.add(originPolygon.getTextureVertexIndices().get(v1));
            listOfTextureVertex.add(originPolygon.getTextureVertexIndices().get(v2));
            listOfTextureVertex.add(originPolygon.getTextureVertexIndices().get(v3));
            newPolygon.setTextureVertexIndices(listOfTextureVertex);
        }

        newPolygon.setTextureVertexIndices(originPolygon.getTextureVertexIndices());
        //Обычные вершины
        ArrayList<Integer> listOfVertex = new ArrayList<>();
        listOfVertex.add(originPolygon.getVertexIndices().get(v1));
        listOfVertex.add(originPolygon.getVertexIndices().get(v2));
        listOfVertex.add(originPolygon.getVertexIndices().get(v3));
        newPolygon.setVertexIndices(listOfVertex);

        return newPolygon;
    }
}
