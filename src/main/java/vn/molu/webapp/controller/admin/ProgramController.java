package vn.molu.webapp.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.molu.common.Constants;
import vn.molu.domain.admin.Program;
import vn.molu.service.admin.ProgramService;
import vn.molu.webapp.command.admin.ProgramCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProgramController extends ApplicationObjectSupport {
    private transient final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private ProgramService programService;

    @RequestMapping("/list/program.html")
    public ModelAndView list(@ModelAttribute(value = Constants.FORM_MODEL_KEY) ProgramCommand command,
                             HttpServletRequest request, BindingResult bindingResult){

        ModelAndView mav = new ModelAndView("admin/program/list");
        String crudaction = command.getCrudaction();
        if(StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_DELETE)){
            if(command.getPojo().getProgramId() != null){
                try {
                    programService.delete(command.getPojo().getProgramId());
                    
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.delete.successful"));
                } catch (Exception e){
                    mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                    mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                    mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                            this.getMessageSourceAccessor().getMessage("msg.database.delete.unsuccessful"));

                    log.error(e.getMessage(), e);
                }
            }
        } else if (StringUtils.isNotBlank(crudaction) && crudaction.equals(Constants.ACTION_INSERT_OR_UPDATE)) {

            if(!bindingResult.hasErrors()){
                Program pojo = command.getPojo();
                if(pojo.getProgramId() == null){ //
                    try {
                        pojo.setProgramName(command.getPojo().getProgramName());
                        pojo.setUrl(command.getPojo().getUrl());

                        pojo = programService.save(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.add.successful"));
                    } catch (Exception e){
                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.add.unsuccessful"));

                        log.error(e.getMessage(), e);
                    }
                }
                else{
                    try {
                        pojo = programService.save(pojo);

                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_SUCCESS);
                        mav.addObject("messageResponse",
                                this.getMessageSourceAccessor().getMessage("msg.database.update.successful"));
                    } catch (Exception e){
                        mav.addObject(Constants.IS_MESSAGE_MODEL_KEY, true);
                        mav.addObject(Constants.MESSAGE_TYPE_MODEL_KEY, Constants.MESSAGE_TYPE_ERROR);
                        mav.addObject(Constants.MESSAGE_RESPONSE_MODEL_KEY,
                                this.getMessageSourceAccessor().getMessage("msg.database.update.unsuccessful"));

                        log.error(e.getMessage(), e);
                    }
                }
                command.setPojo(null);
            }
            else {
                mav.addObject(Constants.IS_MODAL_SHOW, true);
            }
        }

        executeSearch(command);
        mav.addObject(Constants.LIST_MODEL_KEY, command);
        return mav;
    }

    private void executeSearch(ProgramCommand command) {
        List<Program> list = programService.findAll();
        command.setListResult(list);
    }

    @RequestMapping(value = "/ajax/getProgramById.html", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    Program getProgram(@RequestParam("programId") Long programId){
        try {
            Program program = programService.findById(programId);
            return program;
        } catch (Exception e){
            logger.error(e.getMessage());
            return new Program();
        }
    }
}
