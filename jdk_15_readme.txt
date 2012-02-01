data.xml uses the StAX API for parsing XML documents.  Projects on JDK
1.6+ will not need any additional dependencies.  If you are on JDK 1.5, you
will need a StAX implementation.  Below is the XML needed to add that
dependency to your maven POM:

<dependencies>
...
  <dependency>
    <groupId>stax</groupId>
    <artifactId>stax</artifactId>
    <version>1.2.0</version>
  </dependency>
...
</dependencies>

And the Leiningen equivalent:
:dependencies [..
              [stax "1.2.0"]
              ...]

Note that other StAX implementations should work, but the above will
be better tested (and known to work).              
