ContainingJ2P2P
===============

Eclipse
---------------
1) Get the "Maven Integration for Eclipse (Juno and newer)"(570k installs) plugin from help->eclipse marketplace. 
2) Go to File->import: Existing Maven Projects
3) Select p2p as Root Directory & hit Finish

4 \*) Right-click Client->build path->configure build path: Add jars-> sellect all jars in P2P/lib 

IntelliJ
---------------

1) Import module
2) Select Maven
3) set P2P as root dir

4)File->project structure: Modules->dependencies-> "+": Jars or Directories. Apply.
