package un5.eje5_4

import java.io.File
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory
import java.util.logging.Level
import java.util.logging.LogManager

open class CatalogoLibrosXML(private val cargador:String) {
    val archivo = File(cargador)
    val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
    init {
        if(archivo.exists()){
            l.info("El fichero existe")
        }else l.info("El fichero no existe")
    }

    open fun existeLibro(idLibro: String): Boolean {
        val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
        var xmlDoc = readXml("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")

        //https://runebook.dev/es/docs/dom/node/normalize
        xmlDoc.documentElement.normalize()

        val lista = obtenerListaNodosPorNombre(xmlDoc, "book")

        lista.forEach {
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                val mMap = obtenerAtributosEnMapKV(elem)
                val nMap = mMap.toString()
                var ida = "{id=$idLibro}"
                if (ida == nMap) {
                    l.info("true")
                    return true
                } else {
                    val adios = "o"
                }
            }
        }
        l.info("false")
        return false
    }

    open fun infoLibro(idLibro: String): Map<String, Any> {
        val mapVacio = mutableMapOf<String, String>("" to "")
        val l = LogManager.getLogManager().getLogger("").apply { level = Level.ALL }
        var xmlDoc = readXml("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")

        //https://runebook.dev/es/docs/dom/node/normalize
        xmlDoc.documentElement.normalize()

        val lista = obtenerListaNodosPorNombre(xmlDoc, "book")

        lista.forEach {
            if (it.getNodeType() === Node.ELEMENT_NODE) {
                val elem = it as Element
                val mMap = obtenerAtributosEnMapKV(elem)
                val nMap = mMap.toString()
                var ida = "{id=$idLibro}"

                if (ida == nMap) {
                    l.info("true")
                    l.info("- ${it.nodeName} - $mMap")
                    l.info("- Autor: ${it.getElementsByTagName("author").item(0).textContent}")
                    l.info("- Titulo: ${it.getElementsByTagName("title").item(0).textContent}")
                    l.info("- Genero: ${it.getElementsByTagName("genre").item(0).textContent}")
                    l.info("- Precio: ${it.getElementsByTagName("price").item(0).textContent}")
                    l.info("- Fecha de Publicacion: ${it.getElementsByTagName("publish_date").item(0).textContent}")
                    l.info("- Descripcion: ${it.getElementsByTagName("description").item(0).textContent}")

                    var mapa = mapOf(
                        "id" to "$mMap",
                        "Autor" to "${it.getElementsByTagName("author").item(0).textContent}",
                        "Titulo" to "${it.getElementsByTagName("title").item(0).textContent}",
                        "Género" to "${it.getElementsByTagName("genre").item(0).textContent}",
                        "Precio" to "${it.getElementsByTagName("price").item(0).textContent}",
                        "Fecha de Publicacion" to "${it.getElementsByTagName("publish_date").item(0).textContent}",
                        "Descripcion" to "${it.getElementsByTagName("description").item(0).textContent}"
                    )
                    return mapa
                } else {
                    val adios = "o"
                }
            }
        }
        l.info("No se ha encontrado")
        return mapVacio
    }

    private fun readXml(pathName: String): Document {
        val xmlFile = File(pathName)
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile)
    }

    private fun obtenerAtributosEnMapKV(e: Element): MutableMap<String, String> {
        val mMap = mutableMapOf<String, String>()
        for (j in 0..e.attributes.length - 1)
            mMap.putIfAbsent(e.attributes.item(j).nodeName, e.attributes.item(j).nodeValue)
        return mMap
    }

    private fun obtenerListaNodosPorNombre(doc: Document, tagName: String): MutableList<Node> {
        val bookList: NodeList = doc.getElementsByTagName(tagName)
        val lista = mutableListOf<Node>()
        for (i in 0..bookList.length - 1)
            lista.add(bookList.item(i))
        return lista
    }
}

fun main() {
    var texto = CatalogoLibrosXML("..\\5_4\\src\\main\\kotlin\\Catalogo.xml")
    texto.existeLibro("bk101")
    texto.infoLibro("bk102")

/*
<ItemSet>
    <Item type="T0" count="1">
        <Subitem> Valor T0.TT1 </Subitem>
    </Item>
    <Item type="T1" count="2">
        <Subitem> Valor T1.TT1 </Subitem>
    </Item>
    <Item type="T2" count="1">
         <Subitem> Valor T2.TT1 </Subitem>
    </Item>
    <Item type="T3" count="1">
         <Subitem> Valor T3.TT1 </Subitem>
    </Item>
</ItemSet>
*/


}

