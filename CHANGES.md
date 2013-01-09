From 0.0.6 to 0.0.7

- Fixed bug with args to the indentation function (DXML-7)
- Strings now supported as tag names, previously was only kewords (DXML-8)
- Add CDATA and comments support to sexp-as-element (DXML-11)
- data.xml now properly handles CDATA records that contain an embedded ]]>
  by breaking it into two CDATA sections (DXML-12)