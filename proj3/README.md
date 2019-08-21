# Bear Maps
Bear Maps is a simple mapping application which provides some basic feature include zoom in/out, shortest path between two places,
autocomplete the search. For more details, please visit the website 
[https://sp18.datastructur.es/materials/proj/proj3/proj3#table-of-contents](https://sp18.datastructur.es/materials/proj/proj3/proj3#table-of-contents)

### What I did
According to the instruction, I implement three basic classes plus one helper class.
These classes are **Rasterer**, **GraphDB**, **Router**, **Trie**.

The [Rasterer](https://github.com/yeyypp/skeleton-sp18/blob/master/proj3/src/main/java/Rasterer.java) class will take multiple parameters then produce a 2D array of filenames corresponding to the files to be rendered.

The [GraphDB](https://github.com/yeyypp/skeleton-sp18/blob/master/proj3/src/main/java/GraphDB.java) class will read the real dataset and store the data in a graph data structure.

The [Router](https://github.com/yeyypp/skeleton-sp18/blob/master/proj3/src/main/java/Router.java) class will take a start ID and a destination ID and compute the shortest path using the Dijkstra or A* algorithm.

The [Trie](https://github.com/yeyypp/skeleton-sp18/blob/master/proj3/src/main/java/Trie.java) class will store all the location name in the Trie data structure. The getLocationsByPrefix methods will compute all the location's name with the given prefix.
