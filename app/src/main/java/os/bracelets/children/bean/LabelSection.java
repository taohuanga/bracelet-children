package os.bracelets.children.bean;

import aio.health2world.brvah.entity.SectionEntity;

public class LabelSection extends SectionEntity<LabelBean> {

    public LabelSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public LabelSection(LabelBean labelBean) {
        super(labelBean);
    }
}
