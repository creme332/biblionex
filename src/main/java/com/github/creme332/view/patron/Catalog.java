package com.github.creme332.view.patron;

import com.github.creme332.model.Book;
import com.github.creme332.model.Journal;
import com.github.creme332.model.Material;
import com.github.creme332.model.Video;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidIconSizeException;
import com.github.creme332.utils.exception.InvalidPathException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Catalog extends JPanel {
    private static final Dimension ITEM_DIMENSION = new Dimension(200, 200);
    private JPanel itemContainer;

    public Catalog() {
        setLayout(new BorderLayout());
        itemContainer = new JPanel(new GridBagLayout());
        itemContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(createScrollableCatalog(), BorderLayout.CENTER);
    }

    public void displayMaterials(List<Material> materials) {
        int itemCounter = 0;
        GridBagConstraints gbc = new GridBagConstraints();

        for (Material material : materials) {
            JPanel itemCard = createItemCard(material);

            // Add item panel to the catalog
            gbc.gridx = itemCounter % 4;
            gbc.gridy = itemCounter / 4;
            gbc.insets = new Insets(5, 5, 5, 5);
            itemContainer.add(itemCard, gbc);
            itemCounter++;
            itemContainer.revalidate();
            itemContainer.repaint();

            // when user clicks on item card, open a dialog
            itemCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showMaterialDialog(material);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    itemCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true)); // Large white border
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true)); // Revert to default
                                                                                              // border
                }
            });
            ;
        }
    }

    public JPanel createItemCard(Material material) {
        IconLoader iconLoader = new IconLoader();
        Icon icon = null;
        try {
            icon = iconLoader.loadIcon(material.getRelativeImgPath(), 100, 150);
        } catch (InvalidIconSizeException | InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setPreferredSize(ITEM_DIMENSION);
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        // Create image container
        JPanel imageContainer = new JPanel(new BorderLayout());
        JLabel image = new JLabel();
        image.setIcon(icon);
        imageContainer.add(image, BorderLayout.CENTER);

        // Create title container
        JPanel titleContainer = new JPanel(new BorderLayout());

        // Create a title label with word wrapping
        JLabel titleLabel = new JLabel("<html>" + material.getTitle() + "</html>");
        titleLabel.putClientProperty("FlatLaf.style", "font: $semibold.font");

        titleContainer.add(titleLabel, BorderLayout.NORTH);
        titleLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to the item panel
        itemPanel.add(imageContainer);
        itemPanel.add(titleContainer);

        return itemPanel;
    }

    /**
     * Displays a modal with more information about a material.
     * 
     * @param material
     */
    public void showMaterialDialog(Material material) {
        String details = "";
        if (material instanceof Book) {
            Book book = (Book) material;
            details = """
                    <b>Title:</b> %s<br>
                    <b>ISBN:</b> %s<br>
                    <b>Page Count:</b> %d<br>
                    <b>Description:</b> %s<br>
                    <b>Publisher ID:</b> %s<br>
                    <b>Age Restriction:</b> %s
                    """.formatted(book.getTitle(), book.getIsbn(), book.getPageCount(), book.getDescription(),
                    book.getPublisherId(), book.getAgeRestriction());
        } else if (material instanceof Video) {
            Video video = (Video) material;
            details = """
                    <b>Title:</b> %s<br>
                    <b>Language:</b> %s<br>
                    <b>Duration:</b> %d minutes<br>
                    <b>Rating:</b> %s<br>
                    <b>Format:</b> %s<br>
                    <b>Description:</b> %s<br>
                    <b>Publisher ID:</b> %s<br>
                    <b>Age Restriction:</b> %s
                    """.formatted(video.getTitle(), video.getLanguage(), video.getDuration(), video.getRating(),
                    video.getFormat(), video.getDescription(), video.getPublisherId(), video.getAgeRestriction());
        } else if (material instanceof Journal) {
            Journal journal = (Journal) material;
            details = """
                    <b>Title:</b> %s<br>
                    <b>ISSN:</b> %s<br>
                    <b>Website:</b> %s<br>
                    <b>Frequency:</b> %s<br>
                    <b>Start Date:</b> %s<br>
                    <b>Description:</b> %s<br>
                    <b>Publisher ID:</b> %s<br>
                    <b>Age Restriction:</b> %s
                    """.formatted(journal.getTitle(), journal.getIssn(), journal.getWebsite(), journal.getFrequency(),
                    journal.getStartDate(), journal.getDescription(), journal.getPublisherId(),
                    journal.getAgeRestriction());
        }

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("<html>" + details + "</html>");
        textPane.setEditable(false);
        textPane.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, material.getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private JScrollPane createScrollableCatalog() {
        JScrollPane scrollPane = new JScrollPane(itemContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }
}
