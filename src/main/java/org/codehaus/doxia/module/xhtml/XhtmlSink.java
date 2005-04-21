package org.codehaus.doxia.module.xhtml;

import org.codehaus.doxia.module.HTMLSink;
import org.codehaus.doxia.module.xhtml.decoration.render.LinksRenderer;
import org.codehaus.doxia.module.xhtml.decoration.render.NavigationRenderer;
import org.codehaus.doxia.module.xhtml.decoration.render.RenderingContext;
import org.codehaus.doxia.sink.StructureSink;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.xml.PrettyPrintXMLWriter;
import org.codehaus.plexus.util.xml.XMLWriter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

// Note this is highly maven-site centric at the moment with the rendering
// context and the notion of a static site. I want this particular sink to
// funciton in the context of a wiki/blog so some refactoring will be

/**
 * A doxia sink which produces xhtml
 *
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 */
public class XhtmlSink
    extends AbstractXhtmlSink
{
    private StringBuffer buffer = new StringBuffer();

    private boolean headFlag;

    private boolean itemFlag;

    private boolean boxedFlag;

    private boolean verbatimFlag;

    private int cellCount;

    private boolean hasTitle;

    private int sectionLevel;

    private PrintWriter writer;

    private StringsMap directives;

    private RenderingContext renderingContext;

    private String relativePathToBasedir;

    public XhtmlSink( Writer writer, RenderingContext renderingContext, Map directives, String relativePathToBasedir )
    {
        this.writer = new PrintWriter( writer );

        this.directives = new StringsMap( directives );

        this.renderingContext = renderingContext;

        this.relativePathToBasedir = relativePathToBasedir;
    }

    protected void resetState()
    {
        headFlag = false;

        buffer = new StringBuffer();

        itemFlag = false;

        boxedFlag = false;

        verbatimFlag = false;

        cellCount = 0;
    }

    public void head()
    {
        directive( "head()" );

        resetState();

        headFlag = true;
    }

    public void head_()
    {
        headFlag = false;

        directive( "head_()" );
    }

    public void title_()
    {
        if ( buffer.length() > 0 )
        {
            directive( "title()" );

            write( buffer.toString() );

            directive( "title_()" );

            buffer = new StringBuffer();

            hasTitle = true;
        }
    }

    public void author_()
    {
        if ( buffer.length() > 0 )
        {
            buffer = new StringBuffer();
        }
    }

    public void date_()
    {
        if ( buffer.length() > 0 )
        {
            buffer = new StringBuffer();
        }
    }

    public void body()
    {
        String body = directiveValue( "body()" );

        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        NavigationRenderer r = new NavigationRenderer();

        StringWriter sw = new StringWriter();

        XMLWriter w = new PrettyPrintXMLWriter( sw );

        r.render( w, renderingContext );

        Map map = new HashMap();

        map.put( "mainMenu", sw.toString() );

        sw = new StringWriter();

        w = new PrettyPrintXMLWriter( sw );

        LinksRenderer lr = new LinksRenderer();

        lr.render( w, renderingContext );

        map.put( "links", sw.toString()  );

        map.put( "navBarLeft", "Last Published: " + new Date() );

        map.put( "rightColumn", " " );

        body = StringUtils.interpolate( body, map );

        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        write( body );

        //directive( "body()" );
    }

    public void body_()
    {
        String body = directiveValue( "body_()" );

        Map map = new HashMap();

        map.put( "rightColumn", " " );

        map.put( "footer", "Powered by Doxia 2004 (c)" );

        body = StringUtils.interpolate( body, map );

        write( body );

        //directive( "body_()" );

        writer.close();

        resetState();
    }

    // ----------------------------------------------------------------------
    // Sections
    // ----------------------------------------------------------------------

    public void section1()
    {
        directive( "section1()" );
    }

    public void section2()
    {
        directive( "section2()" );
    }

    public void section3()
    {
        directive( "section3()" );
    }

    public void section4()
    {
        directive( "section4()" );
    }

    public void section5()
    {
        directive( "section5()" );
    }

    public void section1_()
    {
        directive( "section1_()" );
    }

    public void section2_()
    {
        directive( "section2_()" );
    }

    public void section3_()
    {
        directive( "section3_()" );
    }

    public void section4_()
    {
        directive( "section4_()" );
    }

    public void section5_()
    {
        directive( "section5_()" );
    }

    public void sectionTitle1()
    {
        directive( "sectionTitle1()" );
    }

    public void sectionTitle1_()
    {
        directive( "sectionTitle1_()" );
    }

    public void sectionTitle2()
    {
        directive( "sectionTitle2()" );
    }

    public void sectionTitle2_()
    {
        directive( "sectionTitle2_()" );
    }

    public void sectionTitle3()
    {
        directive( "sectionTitle3()" );
    }

    public void sectionTitle3_()
    {
        directive( "sectionTitle3_()" );
    }

    public void sectionTitle4()
    {
        directive( "sectionTitle4()" );
    }

    public void sectionTitle4_()
    {
        directive( "sectionTitle4_()" );
    }

    public void sectionTitle5()
    {
        directive( "sectionTitle5()" );
    }

    public void sectionTitle5_()
    {
        directive( "sectionTitle5_()" );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    public void list()
    {
        directive( "list()" );
    }

    public void list_()
    {
        directive( "list_()" );
    }

    public void listItem()
    {
        directive( "listItem()" );

        itemFlag = true;
        // What follows is at least a paragraph.
    }

    public void listItem_()
    {
        directive( "listItem_()" );
    }

    public void numberedList( int numbering )
    {
        directive( "numberedList()" );
    }

    public void numberedList_()
    {
        directive( "numberedList_()" );
    }

    public void numberedListItem()
    {
        directive( "numberedListItem()" );

        itemFlag = true;
        // What follows is at least a paragraph.
    }

    public void numberedListItem_()
    {
        directive( "numberedListItem_()" );
    }

    public void definitionList()
    {
        directive( "definitionList()" );
    }

    public void definitionList_()
    {
        directive( "definitionList_()" );
    }

    public void definedTerm()
    {
        directive( "definedTerm()" );
    }

    public void definedTerm_()
    {
        directive( "definedTerm_()" );
    }

    public void definition()
    {
        directive( "definition()" );

        itemFlag = true;
        // What follows is at least a paragraph.
    }

    public void definition_()
    {
        directive( "definition_()" );
    }

    public void paragraph()
    {
        if ( !itemFlag )
        {
            directive( "paragraph()" );
        }
    }

    public void paragraph_()
    {
        if ( itemFlag )
        {
            itemFlag = false;
        }
        else
        {
            directive( "paragraph_()" );
        }
    }

    public void verbatim( boolean boxed )
    {
        verbatimFlag = true;

        boxedFlag = boxed;

        directive( "verbatim()" );

        if ( boxed )
        {
        }
    }

    public void verbatim_()
    {
        directive( "verbatim_()" );

        if ( boxedFlag )
        {
        }

        verbatimFlag = false;

        boxedFlag = false;
    }

    public void horizontalRule()
    {
        directive( "horizontalRule()" );
    }

    public void table()
    {
        directive( "table()" );
    }

    public void table_()
    {
        directive( "table_()" );
    }

    public void tableRows( int[] justification, boolean grid )
    {
        table();
    }

    public void tableRows_()
    {
        table_();
    }

    private int rowMarker = 0;

    //TODO: could probably make this more flexible but really i would just like a standard xhtml structure.
    public void tableRow()
    {
        if ( rowMarker == 0 )
        {
            write( "<tr class=\"a\"" );

            rowMarker = 1;
        }
        else
        {
            write( "<tr class=\"b\"" );

            rowMarker = 0;
        }

        //directive( "tableRow()" );

        cellCount = 0;
    }

    public void tableRow_()
    {
        directive( "tableRow_()" );

        cellCount = 0;
    }

    public void tableCell()
    {
        tableCell( false );
    }

    public void tableHeaderCell()
    {
        tableCell( true );
    }

    public void tableCell( boolean headerRow )
    {
        if ( headerRow )
        {
            directive( "tableHeaderCell()" );
        }
        else
        {
            directive( "tableCell()" );
        }
    }

    public void tableCell_()
    {
        tableCell_( false );
    }

    public void tableHeaderCell_()
    {
        tableCell_( true );
    }

    public void tableCell_( boolean headerRow )
    {
        if ( headerRow )
        {
            directive( "tableHeaderCell_()" );
        }
        else
        {
            directive( "tableCell_()" );
        }

        ++cellCount;
    }

    public void tableCaption()
    {
        directive( "tableCaption()" );
    }

    public void tableCaption_()
    {
        directive( "tableCaption_()" );
    }

    public void anchor( String name )
    {
        if ( !headFlag )
        {
            write( StringUtils.replaceOnce( directiveValue( "anchor()" ), "$name", name ) );
        }
    }

    public void anchor_()
    {
        if ( !headFlag )
        {
            directive( "link_()" );
        }
    }

    public void link( String name )
    {
        if ( !headFlag )
        {
            write( StringUtils.replaceOnce( directiveValue( "link()" ), "$name", name ) );
        }
    }

    public void link_()
    {
        if ( !headFlag )
        {
            directive( "link_()" );
        }
    }

    public void italic()
    {
        if ( !headFlag )
        {
            directive( "italic()" );
        }
    }

    public void italic_()
    {
        if ( !headFlag )
        {
            directive( "italic_()" );
        }
    }

    public void bold()
    {
        if ( !headFlag )
        {
            directive( "bold()" );
        }
    }

    public void bold_()
    {
        if ( !headFlag )
        {
            directive( "bold_()" );
        }
    }

    public void monospaced()
    {
        if ( !headFlag )
        {
            directive( "monospaced()" );
        }
    }

    public void monospaced_()
    {
        if ( !headFlag )
        {
            directive( "monospaced_()" );
        }
    }

    public void lineBreak()
    {
        if ( headFlag )
        {
            buffer.append( '\n' );
        }
        else
        {
            directive( "lineBreak()" );
        }
    }

    public void nonBreakingSpace()
    {
        if ( headFlag )
        {
            buffer.append( ' ' );
        }
        else
        {
            directive( "nonBreakingSpace()" );
        }
    }

    public void text( String text )
    {
        if ( headFlag )
        {
            buffer.append( text );
        }
        else
        {
            if ( verbatimFlag )
            {
                verbatimContent( text );
            }
            else
            {
                content( text );
            }
        }
    }

    public void rawText( String text )
    {
        write( text );
    }

    // ----------------------------------------------------------------------
    //
    // ----------------------------------------------------------------------

    protected void write( String text )
    {
        if ( relativePathToBasedir != null )
        {
            text = StringUtils.replace( text, "$relativePath", relativePathToBasedir );
        }
        else
        {
            text = StringUtils.replace( text, "$relativePath", "." );
        }

        writer.write( text );
    }

    protected String directiveValue( String key )
    {
        return directives.get( key ) ;
    }

    protected void directive( String key )
    {
        write( directives.get( key ) );
    }

    protected void content( String text )
    {
        write( escapeHTML( text ) );
    }

    protected void verbatimContent( String text )

    {
        write( escapeHTML( text ) );
    }

    public static String escapeHTML( String text )
    {
        return HTMLSink.escapeHTML( text );
    }

    public static String encodeFragment( String text )
    {
        return encodeURL( StructureSink.linkToKey( text ) );
    }

    public static String encodeURL( String text )
    {
        return HTMLSink.encodeURL( text );
    }
}
